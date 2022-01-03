/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ------------------
 * KeyToGroupMap.java
 * ------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.Args;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * A class that maps keys (instances of {@code Comparable}) to groups.
 */
public class KeyToGroupMap<K extends Comparable<K>, G extends Comparable<G>> 
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2228169345475318082L;

    /** The default group. */
    private G defaultGroup;

    /** The groups. */
    private List<G> groups;

    /** A mapping between keys and groups. */
    private Map<K, G> keyToGroupMap;

    /**
     * Creates a new map with a default group named 'Default Group'.
     */
    public KeyToGroupMap() {
        this((G) "Default Group"); // FIXME
    }

    /**
     * Creates a new map with the specified default group.
     *
     * @param defaultGroup  the default group ({@code null} not permitted).
     */
    public KeyToGroupMap(G defaultGroup) {
        Args.nullNotPermitted(defaultGroup, "defaultGroup");
        this.defaultGroup = defaultGroup;
        this.groups = new ArrayList<>();
        this.keyToGroupMap = new HashMap<>();
    }

    /**
     * Returns the number of groups in the map.
     *
     * @return The number of groups in the map.
     */
    public int getGroupCount() {
        return this.groups.size() + 1;
    }

    /**
     * Returns a list of the groups (always including the default group) in the
     * map.  The returned list is independent of the map, so altering the list
     * will have no effect.
     *
     * @return The groups (never {@code null}).
     */
    public List<G> getGroups() {
        List<G> result = new ArrayList<>();
        result.add(this.defaultGroup);
        for (G group : this.groups) {
            if (!result.contains(group)) {
                result.add(group);
            }
        }
        return result;
    }

    /**
     * Returns the index for the group.
     *
     * @param group  the group.
     *
     * @return The group index (or -1 if the group is not represented within
     *         the map).
     */
    public int getGroupIndex(G group) {
        int result = this.groups.indexOf(group);
        if (result < 0) {
            if (this.defaultGroup.equals(group)) {
                result = 0;
            }
        }
        else {
            result = result + 1;
        }
        return result;
    }

    /**
     * Returns the group that a key is mapped to.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The group (never {@code null}, returns the default group if
     *         there is no mapping for the specified key).
     */
    public G getGroup(K key) {
        Args.nullNotPermitted(key, "key");
        G result = this.defaultGroup;
        G group = this.keyToGroupMap.get(key);
        if (group != null) {
            result = group;
        }
        return result;
    }

    /**
     * Maps a key to a group.
     *
     * @param key  the key ({@code null} not permitted).
     * @param group  the group ({@code null} permitted, clears any
     *               existing mapping).
     */
    public void mapKeyToGroup(K key, G group) {
        Args.nullNotPermitted(key, "key");
        G currentGroup = getGroup(key);
        if (!currentGroup.equals(this.defaultGroup)) {
            if (!currentGroup.equals(group)) {
                int count = getKeyCount(currentGroup);
                if (count == 1) {
                    this.groups.remove(currentGroup);
                }
            }
        }
        if (group == null) {
            this.keyToGroupMap.remove(key);
        }
        else {
            if (!this.groups.contains(group)) {
                if (!this.defaultGroup.equals(group)) {
                    this.groups.add(group);
                }
            }
            this.keyToGroupMap.put(key, group);
        }
    }

    /**
     * Returns the number of keys mapped to the specified group.  This method
     * won't always return an accurate result for the default group, since
     * explicit mappings are not required for this group.
     *
     * @param group  the group ({@code null} not permitted).
     *
     * @return The key count.
     */
    public int getKeyCount(G group) {
        Args.nullNotPermitted(group, "group");
        int result = 0;
        for (G g : this.keyToGroupMap.values()) {
            if (group.equals(g)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Tests the map for equality against an arbitrary object.
     *
     * @param obj  the object to test against ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyToGroupMap)) {
            return false;
        }
        KeyToGroupMap<K, G> that = (KeyToGroupMap) obj;
        if (!Objects.equals(this.defaultGroup, that.defaultGroup)) {
            return false;
        }
        if (!this.keyToGroupMap.equals(that.keyToGroupMap)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.defaultGroup);
        hash = 83 * hash + Objects.hashCode(this.keyToGroupMap);
        return hash;
    }

    /**
     * Returns a clone of the map.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if there is a problem cloning the
     *                                     map.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        KeyToGroupMap<K, G> result = (KeyToGroupMap) super.clone();
        result.defaultGroup
            = (G) KeyToGroupMap.clone(this.defaultGroup);
        result.groups = (List<G>) KeyToGroupMap.clone(this.groups);
        result.keyToGroupMap = (Map<K, G>) KeyToGroupMap.clone(this.keyToGroupMap);
        return result;
    }

    /**
     * Attempts to clone the specified object using reflection.
     *
     * @param object  the object ({@code null} permitted).
     *
     * @return The cloned object, or the original object if cloning failed.
     */
    private static Object clone(Object object) {
        if (object == null) {
            return null;
        }
        Class<?> c = object.getClass();
        Object result = null;
        try {
            Method m = c.getMethod("clone", (Class[]) null);
            if (Modifier.isPublic(m.getModifiers())) {
                try {
                    result = m.invoke(object, (Object[]) null);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (NoSuchMethodException e) {
            result = object;
        }
        return result;
    }

    /**
     * Returns a clone of the list.
     *
     * @param list  the list.
     *
     * @return A clone of the list.
     *
     * @throws CloneNotSupportedException if the list could not be cloned.
     */
    private static Collection clone(Collection list)
        throws CloneNotSupportedException {
        Collection result = null;
        if (list != null) {
            try {
                Collection clone = list.getClass().getDeclaredConstructor().newInstance();
                for (Object o : list) {
                    clone.add(KeyToGroupMap.clone(o));
                }
                result = clone;
            }
            catch (Exception e) {
                throw new CloneNotSupportedException("Exception.");
            }
        }
        return result;
    }

}
