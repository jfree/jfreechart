/* 
 * Copyright (C) 2014 Object Refinery Limited and KNIME.com AG
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

var jsfc = {JSFreeChart:{version:"0.5"}};
jsfc.Utils = {};
jsfc.Utils.makeArrayOf = function(a, b) {
  for (var c = [], d = b;d--;) {
    c[d] = a;
  }
  return c;
};
jsfc.Utils.findInArray = function(a, b) {
  for (var c = a.length, d = 0;d < c;d++) {
    if (b(a[d], d)) {
      return d;
    }
  }
  return-1;
};
jsfc.Utils.findItemInArray = function(a, b) {
  return jsfc.Utils.findInArray(b, function(b, d) {
    return b === a;
  });
};
jsfc.Args = {};
jsfc.Args.require = function(a, b) {
  if (null === a) {
    throw Error("Require argument '" + b + "' to be specified.");
  }
  return jsfc.Args;
};
jsfc.Args.requireNumber = function(a, b) {
  if ("number" !== typeof a) {
    throw Error("Require '" + b + "' to be a number.");
  }
  return jsfc.Args;
};
jsfc.Args.requireFinitePositiveNumber = function(a, b) {
  if ("number" !== typeof a || 0 >= a) {
    throw Error("Require '" + b + "' to be a positive number.");
  }
  return jsfc.Args;
};
jsfc.Args.requireInRange = function(a, b, c, d) {
  jsfc.Args.requireNumber(a, b);
  if (a < c || a > d) {
    throw Error("Require '" + b + "' to be in the range " + c + " to " + d);
  }
  return jsfc.Args;
};
jsfc.Args.requireString = function(a, b) {
  if ("string" !== typeof a) {
    throw Error("Require '" + b + "' to be a string.");
  }
  return jsfc.Args;
};
jsfc.Args.requireKeyedValuesDataset = function(a, b) {
  if (!(a instanceof jsfc.KeyedValuesDataset)) {
    throw Error("Require '" + b + "' to be an requireKeyedValuesDataset.");
  }
  return jsfc.Args;
};
jsfc.Args.requireKeyedValues2DDataset = function(a, b) {
  if (!(a instanceof jsfc.KeyedValues2DDataset)) {
    throw Error("Require '" + b + "' to be a KeyedValues2DDataset.");
  }
  return jsfc.Args;
};
jsfc.Args.requireXYDataset = function(a, b) {
  return jsfc.Args;
};
jsfc.Format = function() {
  throw Error("Documents an interface only.");
};
jsfc.Format.prototype.format = function(a) {
};
jsfc.NumberFormat = function(a, b) {
  if (!(this instanceof jsfc.NumberFormat)) {
    throw Error("Use 'new' for construction.");
  }
  this._dp = a;
  this._exponential = b || !1;
};
jsfc.NumberFormat.prototype.format = function(a) {
  jsfc.Args.requireNumber(a, "n");
  return this._exponential ? a.toExponential(this._dp) : this._dp === Number.POSITIVE_INFINITY ? a.toString() : a.toFixed(this._dp);
};
jsfc.DateFormat = function(a) {
  this._date = new Date;
  this._style = a || "d-mmm-yyyy";
  this._months = "Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec".split(" ");
};
jsfc.DateFormat.prototype.format = function(a) {
  jsfc.Args.requireNumber(a, "n");
  this._date.setTime(a);
  return "yyyy" === this._style ? this._dateToYYYY(this._date) : "mmm-yyyy" === this._style ? this._dateToMMMYYYY(this._date) : this._date.toDateString();
};
jsfc.DateFormat.prototype._dateToYYYY = function(a) {
  return a.getFullYear() + "";
};
jsfc.DateFormat.prototype._dateToMMMYYYY = function(a) {
  var b = a.getMonth();
  a = a.getFullYear();
  return this._months[b] + "-" + a;
};
jsfc.RefPt2D = {TOP_LEFT:1, TOP_CENTER:2, TOP_RIGHT:3, CENTER_LEFT:4, CENTER:5, CENTER_RIGHT:6, BOTTOM_LEFT:7, BOTTOM_CENTER:8, BOTTOM_RIGHT:9, isLeft:function(a) {
  return a === jsfc.RefPt2D.TOP_LEFT || a === jsfc.RefPt2D.CENTER_LEFT || a === jsfc.RefPt2D.BOTTOM_LEFT;
}, isRight:function(a) {
  return a === jsfc.RefPt2D.TOP_RIGHT || a === jsfc.RefPt2D.CENTER_RIGHT || a === jsfc.RefPt2D.BOTTOM_RIGHT;
}, isTop:function(a) {
  return a === jsfc.RefPt2D.TOP_LEFT || a === jsfc.RefPt2D.TOP_CENTER || a === jsfc.RefPt2D.TOP_RIGHT;
}, isBottom:function(a) {
  return a === jsfc.RefPt2D.BOTTOM_LEFT || a === jsfc.RefPt2D.BOTTOM_CENTER || a === jsfc.RefPt2D.BOTTOM_RIGHT;
}, isHorizontalCenter:function(a) {
  return a === jsfc.RefPt2D.TOP_CENTER || a === jsfc.RefPt2D.CENTER || a === jsfc.RefPt2D.BOTTOM_CENTER;
}, isVerticalCenter:function(a) {
  return a === jsfc.RefPt2D.CENTER_LEFT || a === jsfc.RefPt2D.CENTER || a === jsfc.RefPt2D.CENTER_RIGHT;
}};
Object.freeze && Object.freeze(jsfc.RefPt2D);
jsfc.Anchor2D = function(a, b) {
  if (!(this instanceof jsfc.Anchor2D)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireNumber(a, "refpt");
  this._refpt = a;
  this._offset = b || new jsfc.Offset2D(0, 0);
};
jsfc.Anchor2D.prototype.refPt = function() {
  return this._refpt;
};
jsfc.Anchor2D.prototype.offset = function() {
  return this._offset;
};
jsfc.Anchor2D.prototype.anchorPoint = function(a) {
  var b = 0, c = 0;
  jsfc.RefPt2D.isLeft(this._refpt) ? b = a.x() + this._offset.dx() : jsfc.RefPt2D.isHorizontalCenter(this._refpt) ? b = a.centerX() : jsfc.RefPt2D.isRight(this._refpt) && (b = a.maxX() - this._offset.dx());
  jsfc.RefPt2D.isTop(this._refpt) ? c = a.minY() + this._offset.dy() : jsfc.RefPt2D.isVerticalCenter(this._refpt) ? c = a.centerY() : jsfc.RefPt2D.isBottom(this._refpt) && (c = a.maxY() - this._offset.dy());
  return new jsfc.Point2D(b, c);
};
jsfc.Shape = function() {
};
jsfc.Shape.prototype.bounds = function() {
};
jsfc.Circle = function(a, b, c) {
  this.x = a;
  this.y = b;
  this.radius = c;
};
jsfc.Circle.prototype.bounds = function() {
  return new jsfc.Rectangle(this.x - this.radius, this.y - this.radius, 2 * this.radius, 2 * this.radius);
};
jsfc.Color = function(a, b, c, d) {
  if (!(this instanceof jsfc.Color)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireInRange(a, "red", 0, 255);
  this._red = a;
  this._green = b;
  this._blue = c;
  this._alpha = 0 === d ? 0 : d || 255;
};
jsfc.Color.prototype.getRed = function() {
  return this._red;
};
jsfc.Color.prototype.getGreen = function() {
  return this._green;
};
jsfc.Color.prototype.getBlue = function() {
  return this._blue;
};
jsfc.Color.prototype.getAlpha = function() {
  return this._alpha;
};
jsfc.Color.fromStr = function(a) {
  if (4 === a.length) {
    var b = a[1] + a[1], c = a[2] + a[2];
    a = a[3] + a[3];
    b = parseInt(b, 16);
    c = parseInt(c, 16);
    a = parseInt(a, 16);
    return new jsfc.Color(b, c, a);
  }
  if (7 === a.length) {
    return b = a[1] + a[2], c = a[3] + a[4], a = a[5] + a[6], b = parseInt(b, 16), c = parseInt(c, 16), a = parseInt(a, 16), new jsfc.Color(b, c, a);
  }
};
jsfc.Color.prototype.rgbaStr = function() {
  return "rgba(" + this._red + "," + this._green + "," + this._blue + "," + (this._alpha / 255).toFixed(2) + ")";
};
jsfc.Color.prototype.rgbStr = function() {
  return "rgb(" + this._red + "," + this._green + "," + this._blue + ")";
};
jsfc.Colors = {};
jsfc.Colors.WHITE = new jsfc.Color(255, 255, 255);
jsfc.Colors.BLACK = new jsfc.Color(0, 0, 0);
jsfc.Colors.RED = new jsfc.Color(255, 0, 0);
jsfc.Colors.GREEN = new jsfc.Color(0, 255, 0);
jsfc.Colors.BLUE = new jsfc.Color(0, 0, 255);
jsfc.Colors.YELLOW = new jsfc.Color(255, 255, 0);
jsfc.Colors.LIGHT_GRAY = new jsfc.Color(192, 192, 192);
jsfc.Colors.fancyLight = function() {
  return "#64E1D5 #E2D75E #F0A4B5 #E7B16D #C2D58D #CCBDE4 #6DE4A8 #93D2E2 #AEE377 #A0D6B5".split(" ");
};
jsfc.Colors.fancyDark = function() {
  return "#3A6163 #8A553A #4A6636 #814C57 #675A6F #384027 #373B43 #59372C #306950 #665D31".split(" ");
};
jsfc.Colors.iceCube = function() {
  return "#4CE4B7 #45756F #C2D9BF #58ADAF #4EE9E1 #839C89 #3E8F74 #92E5C1 #99E5E0 #57BDAB".split(" ");
};
jsfc.Colors.blueOcean = function() {
  return "#6E7094 #4F76DF #292E39 #2E4476 #696A72 #4367A6 #5E62B7 #42759A #2E3A59 #4278CA".split(" ");
};
jsfc.Colors.colorsAsObjects = function(a) {
  return a.map(function(a) {
    return jsfc.Color.fromStr(a);
  });
};
jsfc.Utils2D = {};
jsfc.Utils2D.area2 = function(a, b, c) {
  return(a.x() - c.x()) * (b.y() - c.y()) - (a.y() - c.y()) * (b.x() - c.x());
};
jsfc.Context2D = function() {
};
jsfc.Context2D.prototype.getHint = function(a) {
};
jsfc.Context2D.prototype.setHint = function(a, b) {
};
jsfc.Context2D.prototype.clearHints = function() {
};
jsfc.Context2D.prototype.setLineStroke = function(a) {
};
jsfc.Context2D.prototype.setLineColor = function(a) {
};
jsfc.Context2D.prototype.setFillColor = function(a) {
};
jsfc.Context2D.prototype.drawLine = function(a, b, c, d) {
};
jsfc.Context2D.prototype.drawRect = function(a, b, c, d) {
};
jsfc.Context2D.prototype.fillRect = function(a, b, c, d) {
};
jsfc.Context2D.prototype.drawCircle = function(a, b, c) {
};
jsfc.Context2D.prototype.beginPath = function() {
};
jsfc.Context2D.prototype.closePath = function() {
};
jsfc.Context2D.prototype.moveTo = function(a, b) {
};
jsfc.Context2D.prototype.lineTo = function(a, b) {
};
jsfc.Context2D.prototype.fill = function() {
};
jsfc.Context2D.prototype.stroke = function() {
};
jsfc.Context2D.prototype.getFont = function() {
};
jsfc.Context2D.prototype.setFont = function(a) {
};
jsfc.Context2D.prototype.textDim = function(a) {
};
jsfc.Context2D.prototype.drawString = function(a, b, c) {
};
jsfc.Context2D.prototype.drawAlignedString = function(a, b, c, d) {
};
jsfc.Context2D.prototype.fillText = function(a, b, c, d) {
};
jsfc.Context2D.prototype.translate = function(a, b) {
};
jsfc.Context2D.prototype.rotate = function(a) {
};
jsfc.Context2D.prototype.beginGroup = function(a) {
};
jsfc.Context2D.prototype.endGroup = function() {
};
jsfc.Context2D.prototype.setClip = function(a) {
};
jsfc.Context2D.prototype.save = function() {
};
jsfc.Context2D.prototype.restore = function() {
};
jsfc.Dimension = function(a, b) {
  if (!(this instanceof jsfc.Dimension)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireNumber(a, "w");
  jsfc.Args.requireNumber(b, "h");
  this._width = a;
  this._height = b;
  Object.freeze(this);
};
jsfc.Dimension.prototype.width = function() {
  return this._width;
};
jsfc.Dimension.prototype.height = function() {
  return this._height;
};
jsfc.Fit2D = function(a, b) {
  if (!(this instanceof jsfc.Fit2D)) {
    throw Error("Use 'new' for constructor.");
  }
  this._anchor = a;
  this._scale = b || jsfc.Scale2D.NONE;
};
jsfc.Fit2D.prototype.anchor = function() {
  return this._anchor;
};
jsfc.Fit2D.prototype.scale = function() {
  return this._scale;
};
jsfc.Fit2D.prototype.fit = function(a, b) {
  if (this._scale === jsfc.Scale2D.SCALE_BOTH) {
    return jsfc.Rectangle.copy(b);
  }
  var c = a.width();
  this._scale === jsfc.Scale2D.SCALE_HORIZONTAL && (c = b.width(), jsfc.RefPt2D.isHorizontalCenter(this._anchor.refPt()) || (c -= 2 * this._anchor.offset().dx()));
  var d = a.height();
  this._scale === jsfc.Scale2D.SCALE_VERTICAL && (d = b.height(), jsfc.RefPt2D.isVerticalCenter(this._anchor.refPt()) || (d -= 2 * this._anchor.offset().dy()));
  var e = this._anchor.anchorPoint(b), f = Number.NaN;
  jsfc.RefPt2D.isLeft(this._anchor.refPt()) ? f = e.x() : jsfc.RefPt2D.isHorizontalCenter(this._anchor.refPt()) ? f = b.centerX() - c / 2 : jsfc.RefPt2D.isRight(this._anchor.refPt()) && (f = e.x() - c);
  var g = Number.NaN;
  jsfc.RefPt2D.isTop(this._anchor.refPt()) ? g = e.y() : jsfc.RefPt2D.isVerticalCenter(this._anchor.refPt()) ? g = b.centerY() - d / 2 : jsfc.RefPt2D.isBottom(this._anchor.refPt()) && (g = e.y() - d);
  return new jsfc.Rectangle(f, g, c, d);
};
jsfc.Fit2D.prototype.noScalingFitter = function(a) {
  a = new jsfc.Anchor2D(a, new jsfc.Offset2D(0, 0));
  return new jsfc.Fit2D(a, jsfc.Scale2D.NONE);
};
jsfc.Font = function(a, b, c, d) {
  if (!(this instanceof jsfc.Font)) {
    throw Error("Use 'new' for constructors.");
  }
  this.family = a;
  this.size = b;
  this.bold = c || !1;
  this.italic = d || !1;
};
jsfc.Font.prototype.styleStr = function() {
  var a = "font-family: " + this.family + "; ", a = a + ("font-weight: " + (this.bold ? "bold" : "normal") + "; "), a = a + ("font-style: " + (this.italic ? "italic" : "normal") + "; ");
  return a += "font-size: " + this.size + "px";
};
jsfc.Font.prototype.canvasFontStr = function() {
  return this.size + "px " + this.family;
};
jsfc.HAlign = {LEFT:1, CENTER:2, RIGHT:3};
Object.freeze && Object.freeze(jsfc.HAlign);
jsfc.Insets = function(a, b, c, d) {
  if (!(this instanceof jsfc.Insets)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireNumber(a, "top");
  jsfc.Args.requireNumber(b, "left");
  jsfc.Args.requireNumber(c, "bottom");
  jsfc.Args.requireNumber(d, "right");
  this._top = a;
  this._left = b;
  this._bottom = c;
  this._right = d;
  Object.freeze(this);
};
jsfc.Insets.prototype.top = function() {
  return this._top;
};
jsfc.Insets.prototype.left = function() {
  return this._left;
};
jsfc.Insets.prototype.bottom = function() {
  return this._bottom;
};
jsfc.Insets.prototype.right = function() {
  return this._right;
};
jsfc.Insets.prototype.value = function(a) {
  if (a === jsfc.RectangleEdge.TOP) {
    return this._top;
  }
  if (a === jsfc.RectangleEdge.BOTTOM) {
    return this._bottom;
  }
  if (a === jsfc.RectangleEdge.LEFT) {
    return this._left;
  }
  if (a === jsfc.RectangleEdge.RIGHT) {
    return this._right;
  }
  throw Error("Unrecognised edge code: " + a);
};
jsfc.LineCap = {BUTT:"butt", ROUND:"round", SQUARE:"square"};
Object.freeze && Object.freeze(jsfc.LineCap);
jsfc.LineJoin = {ROUND:"round", BEVEL:"bevel", MITER:"miter"};
Object.freeze && Object.freeze(jsfc.LineJoin);
jsfc.Offset2D = function(a, b) {
  if (!(this instanceof jsfc.Offset2D)) {
    throw Error("Use 'new' for constructors.");
  }
  jsfc.Args.requireNumber(a, "dx");
  jsfc.Args.requireNumber(b, "dy");
  this._dx = a;
  this._dy = b;
  Object.freeze(this);
};
jsfc.Offset2D.prototype.dx = function() {
  return this._dx;
};
jsfc.Offset2D.prototype.dy = function() {
  return this._dy;
};
jsfc.Point2D = function(a, b) {
  if (!(this instanceof jsfc.Point2D)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireNumber(a, "x");
  jsfc.Args.requireNumber(b, "y");
  this._x = a;
  this._y = b;
  Object.freeze(this);
};
jsfc.Point2D.prototype.x = function() {
  return this._x;
};
jsfc.Point2D.prototype.y = function() {
  return this._y;
};
jsfc.Point2D.prototype.distance = function(a, b) {
  var c = a - this._x, d = b - this._y;
  return Math.sqrt(c * c + d * d);
};
jsfc.Polygon = function() {
  if (!(this instanceof jsfc.Polygon)) {
    throw Error("Use 'new' for constructor.");
  }
  this._vertices = [];
};
jsfc.Polygon.prototype.add = function(a) {
  this._vertices.push(a);
  return this;
};
jsfc.Polygon.prototype.getVertexCount = function() {
  return this._vertices.length;
};
jsfc.Polygon.prototype.getVertex = function(a) {
  return this._vertices[a];
};
jsfc.Polygon.prototype.getFirstVertex = function() {
  return 0 < this._vertices.length ? this._vertices[0] : null;
};
jsfc.Polygon.prototype.getLastVertex = function() {
  var a = this._vertices.length;
  return 0 < a ? this._vertices[a - 1] : null;
};
jsfc.Polygon.prototype.contains = function(a) {
  for (var b = this.getVertexCount(), c = b - 1, d = a.y(), e = !1, f = 0;f < b;f++) {
    var g = this._vertices[f], c = this._vertices[c];
    if (c.y() <= d && d < g.y() && 0 < jsfc.Utils2D.area2(c, g, a) || g.y() <= d && d < c.y() && 0 < jsfc.Utils2D.area2(g, c, a)) {
      e = !e;
    }
    c = f;
  }
  return e;
};
jsfc.Rectangle = function(a, b, c, d) {
  if (!(this instanceof jsfc.Rectangle)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireNumber(a, "x");
  jsfc.Args.requireNumber(b, "y");
  jsfc.Args.requireNumber(c, "width");
  jsfc.Args.requireNumber(d, "height");
  this._x = a;
  this._y = b;
  this._width = c;
  this._height = d;
};
jsfc.Rectangle.copy = function(a) {
  return new jsfc.Rectangle(a.x(), a.y(), a.width(), a.height());
};
jsfc.Rectangle.prototype.x = function() {
  return this._x;
};
jsfc.Rectangle.prototype.y = function() {
  return this._y;
};
jsfc.Rectangle.prototype.width = function() {
  return this._width;
};
jsfc.Rectangle.prototype.height = function() {
  return this._height;
};
jsfc.Rectangle.prototype.length = function(a) {
  if (a === jsfc.RectangleEdge.TOP || a === jsfc.RectangleEdge.BOTTOM) {
    return this._width;
  }
  if (a === jsfc.RectangleEdge.LEFT || a === jsfc.RectangleEdge.RIGHT) {
    return this._height;
  }
  throw Error("Unrecognised 'edge' value: " + a);
};
jsfc.Rectangle.prototype.centerX = function() {
  return this._x + this._width / 2;
};
jsfc.Rectangle.prototype.minX = function() {
  return Math.min(this._x, this._x + this._width);
};
jsfc.Rectangle.prototype.maxX = function() {
  return Math.max(this._x, this._x + this._width);
};
jsfc.Rectangle.prototype.centerY = function() {
  return this._y + this._height / 2;
};
jsfc.Rectangle.prototype.minY = function() {
  return Math.min(this._y, this._y + this._height);
};
jsfc.Rectangle.prototype.maxY = function() {
  return Math.max(this._y, this._y + this._height);
};
jsfc.Rectangle.prototype.bounds = function() {
  return new jsfc.Rectangle(this._x, this._y, this._width, this._height);
};
jsfc.Rectangle.prototype.set = function(a, b, c, d) {
  this._x = a;
  this._y = b;
  this._width = c;
  this._height = d;
  return this;
};
jsfc.Rectangle.prototype.constrainedPoint = function(a, b) {
  jsfc.Args.requireNumber(a, "x");
  jsfc.Args.requireNumber(b, "y");
  var c = Math.max(this.minX(), Math.min(a, this.maxX())), d = Math.max(this.minY(), Math.min(b, this.maxY()));
  return new jsfc.Point2D(c, d);
};
jsfc.Rectangle.prototype.contains = function(a, b) {
  return a >= this._x && a <= this._x + this._width && b >= this._y && b <= this._y + this._height;
};
jsfc.Rectangle.prototype.containsRect = function(a) {
  return this.contains(a.minX(), a.minY()) && this.contains(a.maxX(), a.maxY());
};
jsfc.RectangleEdge = {TOP:"TOP", BOTTOM:"BOTTOM", LEFT:"LEFT", RIGHT:"RIGHT"};
jsfc.RectangleEdge.isTopOrBottom = function(a) {
  jsfc.Args.requireString(a, "edge");
  return a === jsfc.RectangleEdge.TOP || a === jsfc.RectangleEdge.BOTTOM ? !0 : !1;
};
jsfc.RectangleEdge.isLeftOrRight = function(a) {
  jsfc.Args.requireString(a, "edge");
  return a === jsfc.RectangleEdge.LEFT || a === jsfc.RectangleEdge.RIGHT ? !0 : !1;
};
Object.freeze && Object.freeze(jsfc.RectangleEdge);
jsfc.Scale2D = {NONE:1, SCALE_HORIZONTAL:2, SCALE_VERTICAL:3, SCALE_BOTH:4};
Object.freeze && Object.freeze(jsfc.Scale2D);
jsfc.Stroke = function(a) {
  if (!(this instanceof jsfc.Stroke)) {
    throw Error("Use 'new' for constructors.");
  }
  this.lineWidth = a || 1;
  this.lineCap = jsfc.LineCap.ROUND;
  this.lineJoin = jsfc.LineJoin.ROUND;
  this.miterLimit = 1;
  this.lineDash = [1];
  this.lineDashOffset = 0;
};
jsfc.Stroke.prototype.setLineDash = function(a) {
  this.lineDash = a;
};
jsfc.Stroke.prototype.getStyleStr = function() {
  var a = "stroke-width: " + this.lineWidth + "; ";
  "butt" !== this.lineCap && (a = a + "stroke-linecap: " + this.lineCap + "; ");
  a = a + "stroke-linejoin: " + this.lineJoin + "; ";
  1 < this.lineDash.length && (a += "stroke-dasharray: 3, 3; ");
  return a;
};
jsfc.SVGLayer = function(a) {
  if (!(this instanceof jsfc.SVGLayer)) {
    throw Error("Use 'new' for constructors.");
  }
  this._id = a;
  this._container = this.createElement("g");
  this._content = this.createElement("g");
  this._container.appendChild(this._content);
  this._stack = [this._content];
  this._defsContainer = this.createElement("g");
  this._defsContent = this.createElement("g");
  this._defsContainer.appendChild(this._defsContent);
};
jsfc.SVGLayer.prototype.getID = function() {
  return this._id;
};
jsfc.SVGLayer.prototype.getContainer = function() {
  return this._container;
};
jsfc.SVGLayer.prototype.getContent = function() {
  return this._content;
};
jsfc.SVGLayer.prototype.getStack = function() {
  return this._stack;
};
jsfc.SVGLayer.prototype.getDefsContainer = function() {
  return this._defsContainer;
};
jsfc.SVGLayer.prototype.getDefsContent = function() {
  return this._defsContent;
};
jsfc.SVGLayer.prototype.clear = function() {
  this._container.removeChild(this._content);
  this._defsContainer.removeChild(this._defsContent);
  this._content = this.createElement("g");
  this._container.appendChild(this._content);
  this._stack = [this._content];
  this._defsContent = this.createElement("g");
  this._defsContainer.appendChild(this._defsContent);
};
jsfc.SVGLayer.prototype.createElement = function(a) {
  return document.createElementNS("http://www.w3.org/2000/svg", a);
};
jsfc.BaseContext2D = function(a) {
  if (!(this instanceof jsfc.BaseContext2D)) {
    throw Error("Use 'new' for construction.");
  }
  a || (a = this);
  jsfc.BaseContext2D.init(a);
};
jsfc.BaseContext2D.init = function(a) {
  a._hints = {};
  a._lineColor = new jsfc.Color(255, 255, 255);
  a._fillColor = new jsfc.Color(255, 0, 0);
  a._font = new jsfc.Font("serif", 12);
};
jsfc.BaseContext2D.prototype.getHint = function(a) {
  return this._hints[a];
};
jsfc.BaseContext2D.prototype.setHint = function(a, b) {
  this._hints[a] = b;
};
jsfc.BaseContext2D.prototype.clearHints = function() {
  this._hints = {};
};
jsfc.BaseContext2D.prototype.setLineStroke = function(a) {
  jsfc.Args.require(a, "stroke");
  this._stroke = a;
};
jsfc.BaseContext2D.prototype.setLineColor = function(a) {
  jsfc.Args.require(a, "color");
  this._lineColor = a;
};
jsfc.BaseContext2D.prototype.setFillColor = function(a) {
  jsfc.Args.require(a, "color");
  this._fillColor = a;
};
jsfc.BaseContext2D.prototype.getFont = function() {
  return this._font;
};
jsfc.BaseContext2D.prototype.setFont = function(a) {
  this._font = a;
};
jsfc.BaseContext2D.prototype.setClip = function(a) {
};
jsfc.BaseContext2D.prototype.save = function() {
};
jsfc.BaseContext2D.prototype.restore = function() {
};
jsfc.SVGContext2D = function(a) {
  if (!(this instanceof jsfc.SVGContext2D)) {
    throw Error("Use 'new' with constructor.");
  }
  jsfc.BaseContext2D.init(this);
  this.svg = a;
  this._defs = this.element("defs");
  this.svg.appendChild(this._defs);
  this._defaultLayer = new jsfc.SVGLayer("default");
  this.svg.appendChild(this._defaultLayer.getContainer());
  this._defs.appendChild(this._defaultLayer.getDefsContainer());
  this._layers = [this._defaultLayer];
  this._currentLayer = this._defaultLayer;
  this._pathStr = "";
  this.textAlign = "start";
  this.textBaseline = "alphabetic";
  this._transform = new jsfc.Transform;
  this._hiddenGroup = this.svg.getElementById("hiddenGroup");
  this._hiddenGroup || (this._hiddenGroup = document.createElementNS("http://www.w3.org/2000/svg", "g"), this._hiddenGroup.setAttribute("id", "hiddenGroup"), this._hiddenGroup.setAttribute("width", 60), this._hiddenGroup.setAttribute("height", 60), this._hiddenGroup.setAttribute("visibility", "hidden"), this.svg.appendChild(this._hiddenGroup));
};
jsfc.SVGContext2D.prototype = new jsfc.BaseContext2D;
jsfc.SVGContext2D.prototype.setHint = function(a, b) {
  if ("layer" === a) {
    var c = this._findLayer(b);
    c || (c = new jsfc.SVGLayer(b + ""), this._addLayer(c));
    this._currentLayer = c;
  } else {
    this._hints[a] = b;
  }
};
jsfc.SVGContext2D.prototype._addLayer = function(a) {
  this._defs.appendChild(a.getDefsContainer());
  this.svg.appendChild(a.getContainer());
  this._layers.push(a);
};
jsfc.SVGContext2D.prototype._removeLayer = function(a) {
  var b = this._indexOfLayer(a);
  if (0 > b) {
    throw Error("The layer is not present in this SVGContext2D.");
  }
  this._layers.splice(b, 1);
  this._defs.removeChild(a.getDefsContainer());
  this.svg.removeChild(a.getContainer());
};
jsfc.SVGContext2D.prototype._indexOfLayer = function(a) {
  for (var b = 0;b < this._layers.length;b++) {
    if (this._layers[b] === a) {
      return b;
    }
  }
  return-1;
};
jsfc.SVGContext2D.prototype._findLayer = function(a) {
  for (var b = 0;b < this._layers.length;b++) {
    if (this._layers[b].getID() === a) {
      return this._layers[b];
    }
  }
};
jsfc.SVGContext2D.prototype.element = function(a) {
  return document.createElementNS("http://www.w3.org/2000/svg", a);
};
jsfc.SVGContext2D.prototype.append = function(a) {
  var b = this._currentLayer.getStack();
  b[b.length - 1].appendChild(a);
};
jsfc.SVGContext2D.prototype.beginGroup = function(a) {
  var b = this.element("g");
  b.setAttribute("class", a);
  if (a = this.getHint("cursor")) {
    b.setAttribute("cursor", a), this.setHint("cursor", null);
  }
  if (a = this.getHint("clip")) {
    var c = this.element("clipPath");
    c.setAttribute("id", "clip-1");
    var d = this._createRectElement(a);
    c.appendChild(d);
    this._currentLayer.getDefsContent().appendChild(c);
    b.setAttribute("clip-path", "url(#clip-1)");
    this.setHint("clip", null);
  }
  this.getHint("glass") && (d = this._createRectElement(a), d.setAttribute("fill", "rgba(0, 0, 0, 0)"), b.appendChild(d));
  this.append(b);
  this._currentLayer.getStack().push(b);
};
jsfc.SVGContext2D.prototype.endGroup = function() {
  var a = this._currentLayer.getStack();
  if (1 === a.length) {
    throw Error("endGroup() does not have a matching beginGroup().");
  }
  a.pop();
};
jsfc.SVGContext2D.prototype.clear = function() {
  this._currentLayer.clear();
};
jsfc.SVGContext2D.prototype.drawLine = function(a, b, c, d) {
  var e = document.createElementNS("http://www.w3.org/2000/svg", "line");
  e.setAttribute("stroke", this._lineColor.rgbaStr());
  e.setAttribute("x1", this._geomDP(a));
  e.setAttribute("y1", this._geomDP(b));
  e.setAttribute("x2", this._geomDP(c));
  e.setAttribute("y2", this._geomDP(d));
  e.setAttribute("style", this._stroke.getStyleStr());
  e.setAttribute("transform", this._svgTransformStr());
  this._setAttributesFromHints(e);
  this.append(e);
};
jsfc.SVGContext2D.prototype.fillRect = function(a, b, c, d) {
  var e = document.createElementNS("http://www.w3.org/2000/svg", "rect");
  e.setAttribute("x", a);
  e.setAttribute("y", b);
  e.setAttribute("width", c);
  e.setAttribute("height", d);
  e.setAttribute("fill", this._fillColor.rgbaStr());
  this.append(e);
};
jsfc.SVGContext2D.prototype.drawRect = function(a, b, c, d) {
  var e = document.createElementNS("http://www.w3.org/2000/svg", "rect");
  e.setAttribute("stroke", this._lineColor.rgbaStr());
  e.setAttribute("fill", this._fillColor.rgbaStr());
  e.setAttribute("x", this._geomDP(a));
  e.setAttribute("y", this._geomDP(b));
  e.setAttribute("width", this._geomDP(c));
  e.setAttribute("height", this._geomDP(d));
  e.setAttribute("style", this._stroke.getStyleStr());
  e.setAttribute("transform", this._svgTransformStr());
  this.append(e);
};
jsfc.SVGContext2D.prototype.drawCircle = function(a, b, c) {
  var d = document.createElementNS("http://www.w3.org/2000/svg", "circle");
  d.setAttribute("stroke", this._lineColor.rgbaStr());
  d.setAttribute("stroke-width", this._stroke.lineWidth);
  d.setAttribute("fill", this._fillColor.rgbaStr());
  d.setAttribute("cx", a);
  d.setAttribute("cy", b);
  d.setAttribute("r", c);
  if (a = this.getHint("ref")) {
    d.setAttribute("jfree:ref", JSON.stringify(a)), this.setHint("ref", null);
  }
  this.append(d);
};
jsfc.SVGContext2D.prototype.drawString = function(a, b, c) {
  this.fillText(a, b, c);
};
jsfc.SVGContext2D.prototype.fillText = function(a, b, c, d) {
  d = document.createElementNS("http://www.w3.org/2000/svg", "text");
  d.setAttribute("x", b);
  d.setAttribute("y", c);
  d.setAttribute("style", this._font.styleStr());
  d.textContent = a;
  this.append(d);
};
jsfc.SVGContext2D.prototype.drawAlignedString = function(a, b, c, d) {
  var e = document.createElementNS("http://www.w3.org/2000/svg", "text");
  e.setAttribute("x", this._geomDP(b));
  e.setAttribute("style", this._font.styleStr());
  e.setAttribute("fill", this._fillColor.rgbaStr());
  e.setAttribute("transform", this._svgTransformStr());
  e.textContent = a;
  b = "start";
  jsfc.TextAnchor.isHorizontalCenter(d) && (b = "middle");
  jsfc.TextAnchor.isRight(d) && (b = "end");
  e.setAttribute("text-anchor", b);
  b = this._font.size;
  jsfc.TextAnchor.isBottom(d) ? b = 0 : jsfc.TextAnchor.isHalfHeight(d) && (b = this._font.size / 2);
  e.setAttribute("y", this._geomDP(c + b));
  this.append(e);
  return this.textDim(a);
};
jsfc.SVGContext2D.prototype.drawRotatedString = function(a, b, c, d, e) {
  this.translate(b, c);
  this.rotate(e);
  this.drawAlignedString(a, 0, 0, d);
  this.rotate(-e);
  this.translate(-b, -c);
};
jsfc.SVGContext2D.prototype._geomDP = function(a) {
  return a.toFixed(3);
};
jsfc.SVGContext2D.prototype.beginPath = function() {
  this._pathStr = "";
};
jsfc.SVGContext2D.prototype.closePath = function() {
  this._pathStr += "Z";
};
jsfc.SVGContext2D.prototype.moveTo = function(a, b) {
  this._pathStr = this._pathStr + "M " + this._geomDP(a) + " " + this._geomDP(b);
};
jsfc.SVGContext2D.prototype.lineTo = function(a, b) {
  this._pathStr = this._pathStr + "L " + this._geomDP(a) + " " + this._geomDP(b);
};
jsfc.SVGContext2D.prototype.arc = function(a, b, c, d, e, f) {
};
jsfc.SVGContext2D.prototype.arcTo = function(a, b, c, d, e) {
};
jsfc.SVGContext2D.prototype.fill = function() {
};
jsfc.SVGContext2D.prototype.stroke = function() {
  var a = document.createElementNS("http://www.w3.org/2000/svg", "path");
  a.setAttribute("style", this._stroke.getStyleStr());
  a.setAttribute("fill", "none");
  a.setAttribute("stroke", "red");
  a.setAttribute("d", this._pathStr);
  this.append(a);
};
jsfc.SVGContext2D.prototype.translate = function(a, b) {
  this._transform.translate(a, b);
};
jsfc.SVGContext2D.prototype.rotate = function(a) {
  this._transform.rotate(a);
};
jsfc.SVGContext2D.prototype._svgTransformStr = function() {
  var a = this._transform;
  return "matrix(" + this._geomDP(a.scaleX) + "," + this._geomDP(a.shearY) + "," + this._geomDP(a.shearX) + "," + this._geomDP(a.scaleY) + "," + this._geomDP(a.translateX) + "," + this._geomDP(a.translateY) + ")";
};
jsfc.SVGContext2D.prototype.textDim = function(a) {
  if (1 !== arguments.length) {
    throw Error("Too many arguments.");
  }
  var b = document.createElementNS("http://www.w3.org/2000/svg", "text");
  b.setAttribute("style", this._font.styleStr());
  b.textContent = a;
  this._hiddenGroup.appendChild(b);
  var c = b.getBBox(), d = new jsfc.Dimension(c.width, c.height);
  0 == c.width && 0 == c.height && 0 < a.length && (c = b.scrollHeight, 0 == c && (c = this.font.size), d = new jsfc.Dimension(b.scrollWidth, c));
  this._hiddenGroup.removeChild(b);
  return d;
};
jsfc.SVGContext2D.prototype._createRectElement = function(a) {
  jsfc.Args.require(a, "rect");
  var b = this.element("rect");
  b.setAttribute("x", a.minX());
  b.setAttribute("y", a.minY());
  b.setAttribute("width", a.width());
  b.setAttribute("height", a.height());
  return b;
};
jsfc.SVGContext2D.prototype._setAttributesFromHints = function(a) {
  var b = this.getHint("pointer-events");
  b && a.setAttribute("pointer-events", b);
};
jsfc.CanvasContext2D = function(a) {
  if (!(this instanceof jsfc.CanvasContext2D)) {
    throw Error("Use 'new' with constructor.");
  }
  jsfc.BaseContext2D.init(this);
  this._canvas = a;
  this._ctx = a.getContext("2d");
};
jsfc.CanvasContext2D.prototype = new jsfc.BaseContext2D;
jsfc.CanvasContext2D.prototype.clear = function() {
  this._ctx.clearRect(0, 0, this._canvas.width, this._canvas.height);
};
jsfc.CanvasContext2D.prototype.setFillColor = function(a) {
  jsfc.BaseContext2D.prototype.setFillColor.call(this, a);
  this._ctx.fillStyle = a.rgbaStr();
};
jsfc.CanvasContext2D.prototype.setLineColor = function(a) {
  jsfc.BaseContext2D.prototype.setLineColor.call(this, a);
  this._ctx.lineStyle = a.rgbaStr();
};
jsfc.CanvasContext2D.prototype.setLineStroke = function(a) {
  jsfc.BaseContext2D.prototype.setLineStroke.call(this, a);
  this._ctx.lineWidth = a.lineWidth;
  this._ctx.lineCap = a.lineCap;
  this._ctx.lineJoin = a.lineJoin;
};
jsfc.CanvasContext2D.prototype.drawLine = function(a, b, c, d) {
  this._ctx.beginPath();
  this._ctx.moveTo(a, b);
  this._ctx.lineTo(c, d);
  this._ctx.stroke();
};
jsfc.CanvasContext2D.prototype.drawRect = function(a, b, c, d) {
  this._ctx.fillRect(a, b, c, d);
};
jsfc.CanvasContext2D.prototype.fillRect = function(a, b, c, d) {
  this._ctx.fillRect(a, b, c, d);
};
jsfc.CanvasContext2D.prototype.drawCircle = function(a, b, c) {
  this._ctx.beginPath();
  this._ctx.arc(a, b, c, 0, 2 * Math.PI);
  this._ctx.fill();
  this._ctx.stroke();
};
jsfc.CanvasContext2D.prototype.beginPath = function() {
  this._ctx.beginPath();
};
jsfc.CanvasContext2D.prototype.closePath = function() {
  this._ctx.closePath();
};
jsfc.CanvasContext2D.prototype.moveTo = function(a, b) {
  this._ctx.moveTo(a, b);
};
jsfc.CanvasContext2D.prototype.lineTo = function(a, b) {
  this._ctx.lineTo(a, b);
};
jsfc.CanvasContext2D.prototype.fill = function() {
  this._ctx.fill();
};
jsfc.CanvasContext2D.prototype.stroke = function() {
  this._ctx.stroke();
};
jsfc.CanvasContext2D.prototype.setFont = function(a) {
  this._font = a;
  this._ctx.font = a.canvasFontStr();
};
jsfc.CanvasContext2D.prototype.textDim = function(a) {
  a = this._ctx.measureText(a).width;
  return new jsfc.Dimension(a, this._font.size);
};
jsfc.CanvasContext2D.prototype.drawString = function(a, b, c) {
  this._ctx.fillText(a, b, c);
};
jsfc.CanvasContext2D.prototype.drawAlignedString = function(a, b, c, d) {
  var e = this.textDim(a), f = 0, g = this._font.size;
  jsfc.TextAnchor.isHorizontalCenter(d) ? f = -e.width() / 2 : jsfc.TextAnchor.isRight(d) && (f = -e.width());
  jsfc.TextAnchor.isBottom(d) ? g = 0 : jsfc.TextAnchor.isHalfHeight(d) && (g = this._font.size / 2);
  this._ctx.fillText(a, b + f, c + g);
  return e;
};
jsfc.CanvasContext2D.prototype.drawRotatedString = function(a, b, c, d, e) {
  this.translate(b, c);
  this.rotate(e);
  this.drawAlignedString(a, 0, 0, d);
  this.rotate(-e);
  this.translate(-b, -c);
};
jsfc.CanvasContext2D.prototype.fillText = function(a, b, c, d) {
  this._ctx.fillText(a, b, c);
};
jsfc.CanvasContext2D.prototype.beginGroup = function(a) {
};
jsfc.CanvasContext2D.prototype.endGroup = function() {
};
jsfc.CanvasContext2D.prototype.translate = function(a, b) {
  this._ctx.translate(a, b);
};
jsfc.CanvasContext2D.prototype.rotate = function(a) {
  this._ctx.rotate(a);
};
jsfc.CanvasContext2D.prototype.setClip = function(a) {
  this._ctx.beginPath();
  this._ctx.rect(a.x(), a.y(), a.width(), a.height());
  this._ctx.clip();
};
jsfc.CanvasContext2D.prototype.save = function() {
  this._ctx.save();
};
jsfc.CanvasContext2D.prototype.restore = function() {
  this._ctx.restore();
};
jsfc.TextAnchor = {TOP_LEFT:0, TOP_CENTER:1, TOP_RIGHT:2, HALF_ASCENT_LEFT:3, HALF_ASCENT_CENTER:4, HALF_ASCENT_RIGHT:5, CENTER_LEFT:6, CENTER:7, CENTER_RIGHT:8, BASELINE_LEFT:9, BASELINE_CENTER:10, BASELINE_RIGHT:11, BOTTOM_LEFT:12, BOTTOM_CENTER:13, BOTTOM_RIGHT:14, isLeft:function(a) {
  return a === jsfc.TextAnchor.TOP_LEFT || a === jsfc.TextAnchor.CENTER_LEFT || a === jsfc.TextAnchor.HALF_ASCENT_LEFT || a === jsfc.TextAnchor.BASELINE_LEFT || a === jsfc.TextAnchor.BOTTOM_LEFT;
}, isHorizontalCenter:function(a) {
  return a === jsfc.TextAnchor.TOP_CENTER || a === jsfc.TextAnchor.CENTER || a === jsfc.TextAnchor.HALF_ASCENT_CENTER || a === jsfc.TextAnchor.BASELINE_CENTER || a === jsfc.TextAnchor.BOTTOM_CENTER;
}, isRight:function(a) {
  return a === jsfc.TextAnchor.TOP_RIGHT || a === jsfc.TextAnchor.CENTER_RIGHT || a === jsfc.TextAnchor.HALF_ASCENT_RIGHT || a === jsfc.TextAnchor.BASELINE_RIGHT || a === jsfc.TextAnchor.BOTTOM_RIGHT;
}, isTop:function(a) {
  return a === jsfc.TextAnchor.TOP_LEFT || a === jsfc.TextAnchor.TOP_CENTER || a === jsfc.TextAnchor.TOP_RIGHT;
}, isHalfAscent:function(a) {
  return a === jsfc.TextAnchor.HALF_ASCENT_LEFT || a === jsfc.TextAnchor.HALF_ASCENT_CENTER || a === jsfc.TextAnchor.HALF_ASCENT_RIGHT;
}, isHalfHeight:function(a) {
  return a === jsfc.TextAnchor.CENTER_LEFT || a === jsfc.TextAnchor.CENTER || a === jsfc.TextAnchor.CENTER_RIGHT;
}, isBaseline:function(a) {
  return a === jsfc.TextAnchor.BASELINE_LEFT || a === jsfc.TextAnchor.BASELINE_CENTER || a === jsfc.TextAnchor.BASELINE_RIGHT;
}, isBottom:function(a) {
  return a === jsfc.TextAnchor.BOTTOM_LEFT || a === jsfc.TextAnchor.BOTTOM_CENTER || a === jsfc.TextAnchor.BOTTOM_RIGHT;
}};
Object.freeze && Object.freeze(jsfc.TextAnchor);
jsfc.Transform = function() {
  if (!(this instanceof jsfc.Transform)) {
    throw Error("Use 'new' for constructors.");
  }
  this.scaleY = this.scaleX = 1;
  this.shearY = this.shearX = this.translateY = this.translateX = 0;
};
jsfc.Transform.prototype.translate = function(a, b) {
  this.translateX += a;
  this.translateY += b;
};
jsfc.Transform.prototype.rotate = function(a) {
  var b = Math.cos(a);
  a = Math.sin(a);
  var c = this.scaleX * -a + this.shearX * b, d = this.shearY * b + this.scaleY * a, e = this.shearY * -a + this.scaleY * b;
  this.scaleX = this.scaleX * b + this.shearX * a;
  this.shearX = c;
  this.shearY = d;
  this.scaleY = e;
};
jsfc.TableElement = function() {
};
jsfc.TableElement.prototype.preferredSize = function(a, b) {
};
jsfc.TableElement.prototype.layoutElements = function(a, b) {
};
jsfc.TableElement.prototype.draw = function(a, b) {
};
jsfc.TableElement.prototype.receive = function(a) {
};
jsfc.BaseElement = function(a) {
  if (!(this instanceof jsfc.BaseElement)) {
    throw Error("Use 'new' for construction.");
  }
  a || (a = this);
  jsfc.BaseElement.init(a);
};
jsfc.BaseElement.init = function(a) {
  a._insets = new jsfc.Insets(2, 2, 2, 2);
  a._refPt = jsfc.RefPt2D.CENTER;
  a._backgroundPainter = null;
};
jsfc.BaseElement.prototype.getInsets = function() {
  return this._insets;
};
jsfc.BaseElement.prototype.setInsets = function(a) {
  this._insets = a;
};
jsfc.BaseElement.prototype.refPt = function(a) {
  if (!arguments.length) {
    return this._refPt;
  }
  this._refPt = a;
  return this;
};
jsfc.BaseElement.prototype.backgroundPainter = function(a) {
  if (!arguments.length) {
    return this._backgroundPainter;
  }
  this._backgroundPainter = a;
  return this;
};
jsfc.BaseElement.prototype.receive = function(a) {
  a(this);
};
jsfc.FlowElement = function() {
  if (!(this instanceof jsfc.FlowElement)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseElement.init(this);
  this._elements = [];
  this._halign = jsfc.HAlign.LEFT;
  this._hgap = 2;
};
jsfc.FlowElement.prototype = new jsfc.BaseElement;
jsfc.FlowElement.prototype.halign = function(a) {
  if (!arguments.length) {
    return this._halign;
  }
  this._halign = a;
  return this;
};
jsfc.FlowElement.prototype.hgap = function(a) {
  if (!arguments.length) {
    return this._hgap;
  }
  this._hgap = a;
  return this;
};
jsfc.FlowElement.prototype.add = function(a) {
  this._elements.push(a);
  return this;
};
jsfc.FlowElement.prototype.receive = function(a) {
  this._elements.forEach(function(b) {
    b.receive(a);
  });
};
jsfc.FlowElement.prototype.preferredSize = function(a, b) {
  for (var c = this.getInsets(), d = c.left() + c.right(), e = c.top() + c.bottom(), f = 0, g = this._elements.length, h = 0;h < g;) {
    var k = this._rowOfElements(h, a, b), l = this._calcRowHeight(k), m = this._calcRowWidth(k, this._hgap), f = Math.max(m, f), e = e + l, h = h + k.length
  }
  d += f;
  return new jsfc.Dimension(c.left() + d + c.right(), c.top() + e + c.bottom());
};
jsfc.FlowElement.prototype._rowOfElements = function(a, b, c) {
  for (var d = [], e = a, f = !1, g = this.getInsets(), g = g.left() + g.right();e < this._elements.length && !f;) {
    var h = this._elements[e], k = h.preferredSize(b, c);
    g + k.width() < c.width() || e === a ? (d.push({element:h, dim:k}), g += k.width() + this._hgap, e++) : f = !0;
  }
  return d;
};
jsfc.FlowElement.prototype._calcRowHeight = function(a) {
  for (var b = 0, c = 0;c < a.length;c++) {
    b = Math.max(b, a[c].dim.height());
  }
  return b;
};
jsfc.FlowElement.prototype._calcRowWidth = function(a) {
  for (var b = 0, c = a.length, d = 0;d < a.length;d++) {
    b += a[d].dim.width();
  }
  1 < c && (b += (c - 1) * this._hgap);
  return b;
};
jsfc.FlowElement.prototype.layoutElements = function(a, b) {
  for (var c = [], d = 0, e = this.getInsets(), f = b.x() + e.left(), g = b.y() + e.top();d < this._elements.length;) {
    var h = this._rowOfElements(d, a, b), k = this._calcRowHeight(h), l = this._calcRowWidth(h);
    this._halign === jsfc.HAlign.CENTER ? f = b.centerX() - l / 2 : this._halign === jsfc.HAlign.RIGHT && (f = b.maxX() - e.right() - l);
    for (l = 0;l < h.length;l++) {
      var m = new jsfc.Rectangle(f, g, h[l].dim.width(), k);
      c.push(m);
      f += m.width() + this._hgap;
    }
    d += h.length;
    f = b.x() + e.left();
    g += k;
  }
  return c;
};
jsfc.FlowElement.prototype.draw = function(a, b) {
  for (var c = this.preferredSize(a, b), c = (new jsfc.Fit2D(new jsfc.Anchor2D(this.refPt()), jsfc.Scale2D.NONE)).fit(c, b), c = this.layoutElements(a, c), d = 0;d < this._elements.length;d++) {
    this._elements[d].draw(a, c[d]);
  }
};
jsfc.GridElement = function() {
  if (!(this instanceof jsfc.GridElement)) {
    throw Error("Use 'new' for construction.");
  }
  jsfc.BaseElement.init(this);
  this._elements = new jsfc.KeyedValues2DDataset;
};
jsfc.GridElement.prototype = new jsfc.BaseElement;
jsfc.GridElement.prototype.add = function(a, b, c) {
  this._elements.add(b, c, a);
  return this;
};
jsfc.GridElement.prototype._findCellDims = function(a, b) {
  for (var c = jsfc.Utils.makeArrayOf(0, this._elements.columnCount()), d = jsfc.Utils.makeArrayOf(0, this._elements.rowCount()), e = 0;e < this._elements.rowCount();e++) {
    for (var f = 0;f < this._elements.columnCount();f++) {
      var g = this._elements.valueByIndex(e, f);
      g && (g = g.preferredSize(a, b), c[f] = Math.max(c[f], g.width()), d[e] = Math.max(d[e], g.height()));
    }
  }
  return{widths:c, heights:d};
};
jsfc.GridElement.prototype.preferredSize = function(a, b) {
  for (var c = this.getInsets(), d = this._findCellDims(a, b), e = c.left() + c.right(), f = 0;f < d.widths.length;f++) {
    e += d.widths[f];
  }
  c = c.top() + c.bottom();
  for (f = 0;f < d.heights.length;f++) {
    c += d.heights[f];
  }
  return new jsfc.Dimension(e, c);
};
jsfc.GridElement.prototype.layoutElements = function(a, b) {
  for (var c = this.getInsets(), d = this._findCellDims(a, b), e = [], f = b.y() + c.top(), g = 0;g < this._elements.rowCount();g++) {
    for (var h = b.x() + c.left(), k = 0;k < this._elements.columnCount();k++) {
      e.push(new jsfc.Rectangle(h, f, d.widths[k], d.heights[g])), h += d.widths[k];
    }
    f += d.heights[g];
  }
  return e;
};
jsfc.GridElement.prototype.draw = function(a, b) {
  for (var c = this.layoutElements(a, b), d = 0;d < this._elements.rowCount();d++) {
    for (var e = 0;e < this._elements.columnCount();e++) {
      var f = this._elements.valueByIndex(d, e);
      if (f) {
        var g = c[d * this._elements.columnCount() + e];
        f.draw(a, g);
      }
    }
  }
};
jsfc.GridElement.prototype.receive = function(a) {
  for (var b = 0;b < this._elements.rowCount();b++) {
    for (var c = 0;c < this._elements.columnCount();c++) {
      var d = this._elements.valueByIndex(b, c);
      null !== d && d.receive(a);
    }
  }
};
jsfc.RectangleElement = function(a, b) {
  if (!(this instanceof jsfc.RectangleElement)) {
    throw Error("Use 'new' for construction.");
  }
  jsfc.BaseElement.init(this);
  this._width = a;
  this._height = b;
  this._fillColor = new jsfc.Color(255, 255, 255);
  this._backgroundPainter = new jsfc.StandardRectanglePainter(new jsfc.Color(255, 255, 255, 0.3), new jsfc.Color(0, 0, 0, 0));
};
jsfc.RectangleElement.prototype = new jsfc.BaseElement;
jsfc.RectangleElement.prototype.width = function(a) {
  if (!arguments.length) {
    return this._width;
  }
  this._width = a;
  return this;
};
jsfc.RectangleElement.prototype.height = function(a) {
  if (!arguments.length) {
    return this._height;
  }
  this._height = a;
  return this;
};
jsfc.RectangleElement.prototype.getFillColor = function() {
  return this._fillColor;
};
jsfc.RectangleElement.prototype.setFillColor = function(a) {
  if ("string" === typeof a) {
    throw Error("needs to be a color");
  }
  this._fillColor = a;
  return this;
};
jsfc.RectangleElement.prototype.preferredSize = function(a, b) {
  var c = this.getInsets(), d = c.left() + this._width + c.right(), c = c.top() + this._height + c.bottom(), e = b.width(), f = b.height();
  return new jsfc.Dimension(Math.min(d, e), Math.min(c, f));
};
jsfc.RectangleElement.prototype.layoutElements = function(a, b) {
  var c = this.getInsets(), d = Math.min(c.left() + this._width + c.right(), b.width()), c = Math.min(c.top() + this._height + c.bottom(), b.height());
  return[new jsfc.Rectangle(b.centerX() - d / 2, b.centerY() - c / 2, d, c)];
};
jsfc.RectangleElement.prototype.draw = function(a, b) {
  var c = this.backgroundPainter();
  c && c.paint(a, b);
  var d = this.getInsets(), c = Math.max(b.width() - d.left() - d.right(), 0), d = Math.max(b.height() - d.top() - d.bottom(), 0), c = Math.min(this._width, c), d = Math.min(this._height, d);
  a.setFillColor(this._fillColor);
  a.fillRect(b.centerX() - c / 2, b.centerY() - d / 2, c, d);
};
jsfc.ShapeElement = function(a, b) {
  jsfc.BaseElement.init(this);
  this.shape = a;
  this.color = b;
};
jsfc.ShapeElement.prototype = new jsfc.BaseElement;
jsfc.ShapeElement.prototype.preferredSize = function(a, b, c) {
  c = this.shape.bounds();
  var d = this.getInsets();
  a = Math.min(b.width, c.width + d.left + d.right);
  b = Math.min(b.height, c.height + d.top + d.bottom);
  return new jsfc.Dimension(a, b);
};
jsfc.ShapeElement.prototype.layoutElements = function(a, b, c) {
  a = this.preferredSize(a, b, c);
  return[new jsfc.Rectangle(b.centerX() - a.width() / 2, b.centerY() - a.height() / 2, a.width(), a.height())];
};
jsfc.ShapeElement.prototype.draw = function(a, b) {
};
jsfc.RectanglePainter = function() {
};
jsfc.RectanglePainter.prototype.paint = function(a, b) {
};
jsfc.StandardRectanglePainter = function(a, b) {
  if (!(this instanceof jsfc.StandardRectanglePainter)) {
    throw Error("Use 'new' for construction.");
  }
  this._fillColor = a;
  this._strokeColor = b;
};
jsfc.StandardRectanglePainter.prototype.paint = function(a, b) {
  this._fillColor && (a.setFillColor(this._fillColor), a.fillRect(b.x(), b.y(), b.width(), b.height()));
  this._strokeColor && a.setLineColor(this._strokeColor);
};
jsfc.TextElement = function(a) {
  if (!(this instanceof jsfc.TextElement)) {
    throw Error("Use 'new' for construction.");
  }
  jsfc.BaseElement.init(this);
  this._text = a;
  this._font = new jsfc.Font("Palatino, serif", 16);
  this._color = new jsfc.Color(0, 0, 0);
  this._halign = jsfc.HAlign.LEFT;
  this._backgroundPainter = new jsfc.StandardRectanglePainter(new jsfc.Color(255, 255, 255, 0.3), new jsfc.Color(0, 0, 0, 0));
};
jsfc.TextElement.prototype = new jsfc.BaseElement;
jsfc.TextElement.prototype.getText = function() {
  return this._text;
};
jsfc.TextElement.prototype.setText = function(a) {
  this._text = a;
  return this;
};
jsfc.TextElement.prototype.getFont = function() {
  return this._font;
};
jsfc.TextElement.prototype.setFont = function(a) {
  this._font = a;
  return this;
};
jsfc.TextElement.prototype.getColor = function() {
  return this._color;
};
jsfc.TextElement.prototype.setColor = function(a) {
  this._color = a;
  return this;
};
jsfc.TextElement.prototype.text = function(a) {
  throw Error("Use get/setText()");
};
jsfc.TextElement.prototype.color = function(a) {
  throw Error("Use get/setColor()");
};
jsfc.TextElement.prototype.font = function(a) {
  throw Error("Use get/setFont().");
};
jsfc.TextElement.prototype.halign = function(a) {
  if (!arguments.length) {
    return this._halign;
  }
  this._halign = a;
  return this;
};
jsfc.TextElement.prototype.preferredSize = function(a, b) {
  var c = this.getInsets();
  a.setFont(this._font);
  var d = a.textDim(this._text);
  return new jsfc.Dimension(c.left() + d.width() + c.right(), c.top() + d.height() + c.bottom());
};
jsfc.TextElement.prototype.layoutElements = function(a, b) {
  var c = this.getInsets();
  a.setFont(this._font);
  var d = a.textDim(this._text), e = d.width() + c.left() + c.right(), f = b.x();
  switch(this._halign) {
    case jsfc.HAlign.LEFT:
      f = b.x();
      break;
    case jsfc.HAlign.CENTER:
      f = b.centerX() - e / 2;
      break;
    case jsfc.HAlign.RIGHT:
      f = b.maxX() - e;
  }
  var g = b.y(), c = Math.min(d.height() + c.top() + c.bottom(), b.height());
  return[new jsfc.Rectangle(f, g, e, c)];
};
jsfc.TextElement.prototype.draw = function(a, b) {
  var c = this.backgroundPainter();
  c && c.paint(a, b);
  var c = this.getInsets(), d = this.layoutElements(a, b)[0];
  a.setFillColor(this._color);
  a.setFont(this._font);
  var e = d.y() + c.top(), f = d.maxY() - c.bottom(), e = f - 0.18 * (f - e);
  a.drawString(this._text, d.x() + c.left(), e);
};
jsfc.Bin = function(a, b, c, d) {
  this.xmin = a;
  this.xmax = b;
  this.incMin = !1 !== c;
  this.incMax = !1 !== d;
  this.count = 0;
};
jsfc.Bin.prototype.includes = function(a) {
  return a < this.xmin ? !1 : a === this.xmin ? this.incMin : a > this.xmax ? !1 : a === this.xmax ? this.incMax : !0;
};
jsfc.Bin.prototype.overlaps = function(a) {
  return this.xmax < a.xmin || this.xmin > a.xmax || !(this.xmax !== a.xmin || this.incMax && a.incMin) || !(this.xmin !== a.xmax || this.incMin && a.incMax) ? !1 : !0;
};
jsfc.Values2DDataset = function() {
  throw Error("Interface only.");
};
jsfc.Values2DDataset.prototype.add = function(a, b, c, d) {
};
jsfc.Values2DDataset.prototype.rowCount = function() {
};
jsfc.Values2DDataset.prototype.columnCount = function() {
};
jsfc.Values2DDataset.prototype.valueByIndex = function(a, b) {
};
jsfc.Values2DDataset.prototype.rowKeys = function() {
};
jsfc.Values2DDataset.prototype.columnKeys = function() {
};
jsfc.Values2DDataset.prototype.rowKey = function(a) {
};
jsfc.Values2DDataset.prototype.rowIndex = function(a) {
};
jsfc.Values2DDataset.prototype.columnKey = function(a) {
};
jsfc.Values2DDataset.prototype.columnIndex = function(a) {
};
jsfc.Values2DDataset.prototype.valueByKey = function(a, b) {
};
jsfc.Values2DDataset.prototype.getRowPropertyKeys = function(a) {
};
jsfc.Values2DDataset.prototype.getRowProperty = function(a, b) {
};
jsfc.Values2DDataset.prototype.setRowProperty = function(a, b, c, d) {
};
jsfc.Values2DDataset.prototype.clearRowProperties = function(a, b) {
};
jsfc.Values2DDataset.prototype.getColumnPropertyKeys = function(a) {
};
jsfc.Values2DDataset.prototype.getColumnProperty = function(a, b) {
};
jsfc.Values2DDataset.prototype.setColumnProperty = function(a, b, c, d) {
};
jsfc.Values2DDataset.prototype.clearColumnProperties = function(a, b) {
};
jsfc.Values2DDataset.prototype.getItemProperty = function(a, b, c) {
};
jsfc.Values2DDataset.prototype.setItemProperty = function(a, b, c, d, e) {
};
jsfc.Values2DDataset.prototype.getItemPropertyKeys = function(a, b) {
};
jsfc.Values2DDataset.prototype.clearItemProperties = function(a, b, c) {
};
jsfc.XYDataset = function() {
  throw Error("Interface only.");
};
jsfc.XYDataset.prototype.getProperty = function(a) {
};
jsfc.XYDataset.prototype.setProperty = function(a, b, c) {
};
jsfc.XYDataset.prototype.getPropertyKeys = function() {
};
jsfc.XYDataset.prototype.clearProperties = function(a) {
};
jsfc.XYDataset.prototype.seriesCount = function() {
};
jsfc.XYDataset.prototype.seriesKeys = function() {
};
jsfc.XYDataset.prototype.seriesKey = function(a) {
};
jsfc.XYDataset.prototype.seriesIndex = function(a) {
};
jsfc.XYDataset.prototype.itemCount = function(a) {
};
jsfc.XYDataset.prototype.itemIndex = function(a, b) {
};
jsfc.XYDataset.prototype.x = function(a, b) {
};
jsfc.XYDataset.prototype.y = function(a, b) {
};
jsfc.XYDataset.prototype.item = function(a, b) {
};
jsfc.XYDataset.prototype.addListener = function(a) {
};
jsfc.XYDataset.prototype.removeListener = function(a) {
};
jsfc.XYDataset.prototype.bounds = function() {
};
jsfc.XYDataset.prototype.xbounds = function() {
};
jsfc.XYDataset.prototype.ybounds = function() {
};
jsfc.XYDataset.prototype.getItemProperty = function(a, b, c) {
};
jsfc.XYDataset.prototype.setItemProperty = function(a, b, c, d, e) {
};
jsfc.XYDataset.prototype.select = function(a, b, c, d) {
};
jsfc.XYDataset.prototype.unselect = function(a, b, c) {
};
jsfc.XYDataset.prototype.isSelected = function(a, b, c) {
};
jsfc.XYDataset.prototype.clearSelection = function(a) {
};
jsfc.IntervalXYDataset = function() {
  throw Error("Interface only.");
};
jsfc.IntervalXYDataset.prototype.getProperty = function(a) {
};
jsfc.IntervalXYDataset.prototype.setProperty = function(a, b, c) {
};
jsfc.IntervalXYDataset.prototype.getPropertyKeys = function() {
};
jsfc.IntervalXYDataset.prototype.clearProperties = function(a) {
};
jsfc.IntervalXYDataset.prototype.seriesCount = function() {
};
jsfc.IntervalXYDataset.prototype.seriesKeys = function() {
};
jsfc.IntervalXYDataset.prototype.seriesKey = function(a) {
};
jsfc.IntervalXYDataset.prototype.seriesIndex = function(a) {
};
jsfc.IntervalXYDataset.prototype.itemCount = function(a) {
};
jsfc.IntervalXYDataset.prototype.itemIndex = function(a, b) {
};
jsfc.IntervalXYDataset.prototype.x = function(a, b) {
};
jsfc.IntervalXYDataset.prototype.y = function(a, b) {
};
jsfc.IntervalXYDataset.prototype.item = function(a, b) {
};
jsfc.IntervalXYDataset.prototype.addListener = function(a) {
};
jsfc.IntervalXYDataset.prototype.removeListener = function(a) {
};
jsfc.IntervalXYDataset.prototype.bounds = function() {
};
jsfc.IntervalXYDataset.prototype.xbounds = function() {
};
jsfc.IntervalXYDataset.prototype.ybounds = function() {
};
jsfc.IntervalXYDataset.prototype.getItemProperty = function(a, b, c) {
};
jsfc.IntervalXYDataset.prototype.select = function(a, b, c, d) {
};
jsfc.IntervalXYDataset.prototype.unselect = function(a, b, c) {
};
jsfc.IntervalXYDataset.prototype.isSelected = function(a, b, c) {
};
jsfc.IntervalXYDataset.prototype.clearSelection = function(a) {
};
jsfc.IntervalXYDataset.prototype.xStart = function(a, b) {
};
jsfc.IntervalXYDataset.prototype.xEnd = function(a, b) {
};
jsfc.XYDatasetUtils = {};
jsfc.XYDatasetUtils.itemCount = function(a) {
  for (var b = 0, c = 0;c < a.seriesCount();c++) {
    b += a.itemCount(c);
  }
  return b;
};
jsfc.XYDatasetUtils.ybounds = function(a, b) {
  for (var c = b ? b : Number.POSITIVE_INFINITY, d = b ? b : Number.NEGATIVE_INFINITY, e = 0;e < a.seriesCount();e++) {
    for (var f = 0;f < a.itemCount(e);f++) {
      var g = a.y(e, f), c = Math.min(c, g), d = Math.max(d, g)
    }
  }
  return[c, d];
};
jsfc.StandardXYDataset = function() {
  this.data = {series:[]};
  this.properties = {dataset:null, series:[]};
  this.selections = [];
  this._index = {series:new jsfc.Map, items:[]};
  this._listeners = [];
};
jsfc.StandardXYDataset.prototype.seriesCount = function() {
  return this.data.series.length;
};
jsfc.StandardXYDataset.prototype.seriesKeys = function() {
  return this.data.series.map(function(a) {
    return a.seriesKey;
  });
};
jsfc.StandardXYDataset.prototype.seriesKey = function(a) {
  return this.data.series[a].seriesKey;
};
jsfc.StandardXYDataset.prototype.seriesIndex = function(a) {
  jsfc.Args.requireString(a, "seriesKey");
  a = +this._index.series.get(a);
  return 0 <= a ? a : -1;
};
jsfc.StandardXYDataset.prototype.itemCount = function(a) {
  return this.data.series[a].items.length;
};
jsfc.StandardXYDataset.prototype.itemKey = function(a, b) {
  return this.data.series[a].items[b].key;
};
jsfc.StandardXYDataset.prototype.itemIndex = function(a, b) {
  jsfc.Args.require(b, "itemKey");
  var c = this.seriesIndex(a), c = this._index.items[c].get(b);
  return 0 <= c ? c : -1;
};
jsfc.StandardXYDataset.prototype.x = function(a, b) {
  return this.data.series[a].items[b].x;
};
jsfc.StandardXYDataset.prototype.y = function(a, b) {
  return this.data.series[a].items[b].y;
};
jsfc.StandardXYDataset.prototype.item = function(a, b) {
  return this.data.series[a].items[b];
};
jsfc.StandardXYDataset.prototype.itemByKey = function(a, b) {
  for (var c = this.seriesIndex(a), c = this.data.series[c].items, d = 0;d < c.length;d++) {
    if (c[d].key === b) {
      return c[d];
    }
  }
  return null;
};
jsfc.StandardXYDataset.prototype.getItemKey = function(a, b) {
  return this.item(a, b).key;
};
jsfc.StandardXYDataset.prototype.generateItemKey = function(a) {
  if (0 > a) {
    return "0";
  }
  a = this._index.items[a];
  for (var b = a.get("_nextFreeKey_");a.contains("" + b);) {
    b++, a.put("_nextFreeKey_", b);
  }
  return "" + b;
};
jsfc.StandardXYDataset.prototype.items = function(a) {
  return this.data.series[a].items;
};
jsfc.StandardXYDataset.prototype.allItems = function() {
  for (var a = [], b = 0;b < this.data.series.length;b++) {
    a.push(this.items(b));
  }
  return a;
};
jsfc.StandardXYDataset.prototype.add = function(a, b, c, d) {
  jsfc.Args.requireNumber(b, "x");
  var e = this.seriesIndex(a);
  0 > e && (this.addSeries(a), e = this.data.series.length - 1);
  a = this.generateItemKey(e);
  var f = this.data.series[e].items;
  f.push({x:b, y:c, key:a});
  this._index.items[e].put(a, f.length - 1);
  this.properties.series[e].maps.push(null);
  !1 !== d && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype.addByKey = function(a, b, c, d, e) {
  jsfc.Args.requireString(a, "seriesKey");
  var f = this.seriesIndex(a);
  0 > f && (this.addSeries(a), f = this.data.series.length - 1);
  (a = this.itemByKey(a, b)) ? (a.x = c, a.y = d) : (a = this.data.series[f].items, a.push({x:c, y:d, key:b}), this._index.items[f].put(b, a.length - 1), this.properties.series[f].maps.push(null));
  !1 !== e && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype.remove = function(a, b, c) {
  var d = this.itemKey(a, b);
  this.data.series[a].items.splice(b, 1);
  this.properties.series[a].maps.splice(b, 1);
  for (this._index.items[a].remove(d);b < this.itemCount(a);b++) {
    this._index.items[a].put(this.itemKey(a, b), b);
  }
  !1 !== c && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype.removeByKey = function(a, b, c) {
  var d = this.seriesIndex(a);
  a = this.itemIndex(a, b);
  this.remove(d, a, c);
};
jsfc.StandardXYDataset.prototype.addSeries = function(a) {
  if ("string" !== typeof a) {
    throw Error("The 'seriesKey' must be a string.");
  }
  if (this._index.series.contains(a)) {
    throw Error("Duplicate key '" + a);
  }
  this.data.series.push({seriesKey:a, items:[]});
  this._index.series.put(a, this.data.series.length - 1);
  var b = new jsfc.Map;
  b.put("_nextFreeKey_", 0);
  this._index.items.push(b);
  this.properties.series.push({seriesKey:a, seriesProperties:null, maps:[]});
  return this;
};
jsfc.StandardXYDataset.prototype.removeSeries = function(a, b) {
  if ("string" !== typeof a) {
    throw Error("The 'seriesKey' must be a string.");
  }
  var c = this.seriesIndex(a);
  if (0 <= c) {
    this.data.series.splice(c, 1);
    this.properties.series.splice(c, 1);
    this._index.series.remove(a);
    for (this._index.items.splice(c, 1);c < this.seriesCount();c++) {
      var d = this.seriesKey(c);
      this._index.series.put(d, c);
    }
    !1 !== b && this.notifyListeners();
  } else {
    throw Error("No series with that key. " + a);
  }
  return this;
};
jsfc.StandardXYDataset.prototype.bounds = function() {
  for (var a = Number.POSITIVE_INFINITY, b = Number.NEGATIVE_INFINITY, c = Number.POSITIVE_INFINITY, d = Number.NEGATIVE_INFINITY, e = 0;e < this.seriesCount();e++) {
    for (var f = 0;f < this.itemCount(e);f++) {
      var g = this.item(e, f), a = Math.min(a, g.x), b = Math.max(b, g.x), c = Math.min(c, g.y), d = Math.max(d, g.y)
    }
  }
  return[a, b, c, d];
};
jsfc.StandardXYDataset.prototype.xbounds = function() {
  for (var a = Number.POSITIVE_INFINITY, b = Number.NEGATIVE_INFINITY, c = 0;c < this.seriesCount();c++) {
    for (var d = 0;d < this.itemCount(c);d++) {
      var e = this.x(c, d), a = Math.min(a, e), b = Math.max(b, e)
    }
  }
  return[a, b];
};
jsfc.StandardXYDataset.prototype.ybounds = function() {
  for (var a = Number.POSITIVE_INFINITY, b = Number.NEGATIVE_INFINITY, c = 0;c < this.seriesCount();c++) {
    for (var d = 0;d < this.itemCount(c);d++) {
      var e = this.y(c, d), a = Math.min(a, e), b = Math.max(b, e)
    }
  }
  return[a, b];
};
jsfc.StandardXYDataset.prototype.getProperty = function(a) {
  var b = this.properties.dataset;
  if (b) {
    return b.get(a);
  }
};
jsfc.StandardXYDataset.prototype.setProperty = function(a, b, c) {
  this.properties.dataset || (this.properties.dataset = new jsfc.Map);
  this.properties.dataset.put(a, b);
  !1 !== c && this.notifyListeners();
};
jsfc.StandardXYDataset.prototype.getPropertyKeys = function() {
  return this.properties.dataset ? this.properties.dataset.keys() : [];
};
jsfc.StandardXYDataset.prototype.clearProperties = function(a) {
  this.properties.dataset = null;
  !1 !== a && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype.getSeriesPropertyKeys = function(a) {
  a = this.seriesIndex(a);
  return(a = this.properties.series[a].seriesProperties) ? a.keys() : [];
};
jsfc.StandardXYDataset.prototype.getSeriesProperty = function(a, b) {
  var c = this.seriesIndex(a);
  if (c = this.properties.series[c].seriesProperties) {
    return c.get(b);
  }
};
jsfc.StandardXYDataset.prototype.setSeriesProperty = function(a, b, c) {
  a = this.seriesIndex(a);
  var d = this.properties.series[a].seriesProperties;
  d || (d = new jsfc.Map, this.properties.series[a].seriesProperties = d);
  d.put(b, c);
};
jsfc.StandardXYDataset.prototype.clearSeriesProperties = function(a) {
  a = this.seriesIndex(a);
  this.properties.series[a].seriesProperties = null;
};
jsfc.StandardXYDataset.prototype.getItemProperty = function(a, b, c) {
  var d = this.seriesIndex(a);
  a = this.itemIndex(a, b);
  return this.getItemPropertyByIndex(d, a, c);
};
jsfc.StandardXYDataset.prototype.setItemProperty = function(a, b, c, d, e) {
  var f = this.seriesIndex(a);
  a = this.itemIndex(a, b);
  this.setItemPropertyByIndex(f, a, c, d, e);
};
jsfc.StandardXYDataset.prototype.getItemPropertyByIndex = function(a, b, c) {
  if (a = this.properties.series[a].maps[b]) {
    return a.get(c);
  }
};
jsfc.StandardXYDataset.prototype.setItemPropertyByIndex = function(a, b, c, d, e) {
  var f = this.properties.series[a].maps[b];
  f || (f = new jsfc.Map, this.properties.series[a].maps[b] = f);
  f.put(c, d);
  !1 !== e && this.notifyListeners();
};
jsfc.StandardXYDataset.prototype.clearItemProperties = function(a, b) {
  var c = this.seriesIndex(a), d = this.itemIndex(a, b);
  this.properties.series[c].maps[d] = null;
};
jsfc.StandardXYDataset.prototype.select = function(a, b, c, d) {
  var e = this._indexOfSelection(a);
  0 > e ? (a = {id:a, items:[]}, this.selections.push(a)) : a = this.selections[e];
  0 > jsfc.Utils.findInArray(a.items, function(a) {
    return a.seriesKey === b && a.itemKey === c;
  }) && a.items.push({seriesKey:b, itemKey:c});
  !1 !== d && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype.unselect = function(a, b, c, d) {
  a = this._indexOfSelection(a);
  if (0 <= a) {
    a = this.selections[a];
    var e = jsfc.Utils.findInArray(a.items, function(a, d) {
      return a.seriesKey === b && a.itemKey === c;
    });
    0 <= e && a.items.splice(e, 1);
  }
  !1 !== d && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype.isSelected = function(a, b, c) {
  a = this._indexOfSelection(a);
  return 0 > a ? !1 : 0 <= jsfc.Utils.findInArray(this.selections[a].items, function(a) {
    return a.seriesKey === b && a.itemKey === c;
  });
};
jsfc.StandardXYDataset.prototype.clearSelection = function(a, b) {
  var c = this._indexOfSelection(a);
  0 <= c && this.selections.splice(c, 1);
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.StandardXYDataset.prototype._indexOfSelection = function(a) {
  return jsfc.Utils.findInArray(this.selections, function(b) {
    return b.id === a;
  });
};
jsfc.StandardXYDataset.prototype.addListener = function(a) {
  this._listeners.push(a);
  return this;
};
jsfc.StandardXYDataset.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.StandardXYDataset.prototype.notifyListeners = function() {
  for (var a = 0;a < this._listeners.length;a++) {
    this._listeners[a](this);
  }
  return this;
};
jsfc.DatasetUtils = {};
jsfc.DatasetUtils.extractStackBaseValues = function(a, b) {
  b = "undefined" !== typeof b ? b : 0;
  for (var c = new jsfc.KeyedValues2DDataset, d = a.columnCount(), e = a.rowCount(), f = 0;f < d;f++) {
    for (var g = a.columnKey(f), h = b, k = b, l = 0;l < e;l++) {
      var m = a.valueByIndex(l, f), n = a.rowKey(l);
      0 < l ? 0 <= m ? c.add(n, g, h) : c.add(n, g, k) : c.add(n, g, b);
      0 < m && (h += m);
      0 > m && (k += m);
    }
  }
  return c;
};
jsfc.DatasetUtils.extractXYDatasetFromColumns2D = function(a, b, c, d) {
  jsfc.Args.requireString(b, "xcol");
  jsfc.Args.requireString(c, "ycol");
  var e = new jsfc.StandardXYDataset;
  d = d || "series 1";
  for (var f = 0;f < a.rowCount();f++) {
    var g = a.rowKey(f), h = a.valueByKey(g, b), k = a.valueByKey(g, c);
    e.add(d, h, k);
    var h = a.getRowPropertyKeys(g), k = a.getItemPropertyKeys(g, b), l = a.getItemPropertyKeys(g, c), m = e.itemCount(0) - 1;
    h.forEach(function(b) {
      var c = a.getRowProperty(g, b);
      e.setItemPropertyByIndex(0, m, b, c);
    });
    k.forEach(function(c) {
      var d = a.getItemProperty(g, b, c);
      e.setItemPropertyByIndex(0, m, c, d);
    });
    l.forEach(function(b) {
      var d = a.getItemProperty(g, c, b);
      e.setItemPropertyByIndex(0, m, b, d);
    });
  }
  (d = a.getColumnProperty(b, "symbols")) && e.setProperty("x-symbols", d);
  (d = a.getColumnProperty(c, "symbols")) && e.setProperty("y-symbols", d);
  return e;
};
jsfc.DatasetUtils.extractXYDatasetFromRows2D = function(a, b, c, d) {
  var e = new jsfc.StandardXYDataset;
  d = d || "series 1";
  for (var f = 0;f < a.columnCount();f++) {
    var g = a.columnKey(f), h = a.valueByKey(b, g), k = a.valueByKey(c, g);
    e.add(d, h, k);
    var h = a.getColumnPropertyKeys(g), k = a.getItemPropertyKeys(b, g), l = a.getItemPropertyKeys(c, g), m = e.getItemKey(0, e.itemCount(0) - 1);
    h.forEach(function(b) {
      var c = a.getColumnProperty(g, b);
      e.setItemProperty(d, m, b, c);
    });
    k.forEach(function(c) {
      var f = a.getItemProperty(b, g, c);
      e.setItemProperty(d, m, c, f);
    });
    l.forEach(function(b) {
      var f = a.getItemProperty(c, g, b);
      e.setItemProperty(d, m, b, f);
    });
  }
  (f = a.getRowProperty(b, "symbols")) && e.setProperty("x-symbols", f);
  (f = a.getRowProperty(c, "symbols")) && e.setProperty("y-symbols", f);
  return e;
};
jsfc.DatasetUtils.extractXYDatasetFromColumns = function(a, b, c) {
  for (var d = new jsfc.StandardXYDataset, e = 0;e < a.seriesCount();e++) {
    for (var f = a.seriesKey(e), g = 0;g < a.rowCount();g++) {
      var h = a.rowKey(g), k = a.valueByKey(f, h, b);
      if (null !== k) {
        var l = a.propertyKeys(f, h, b), m = a.valueByKey(f, h, c), n = a.propertyKeys(f, h, c);
        d.add(f, k, m);
        var p = d.getItemKey(e, d.itemCount(e) - 1);
        l.forEach(function(c) {
          var e = a.getProperty(f, h, b, c);
          d.setItemProperty(f, p, c, e);
        });
        n.forEach(function(b) {
          var e = a.getProperty(f, h, c, b);
          d.setItemProperty(f, p, b, e);
        });
      }
    }
  }
  return d;
};
jsfc.DatasetUtils.extractXYDatasetFromRows = function(a, b, c) {
  for (var d = new jsfc.StandardXYDataset, e = 0;e < a.seriesCount();e++) {
    for (var f = a.seriesKey(e), g = 0;g < a.columnCount();g++) {
      var h = a.columnKey(g), k = a.valueByKey(f, b, h);
      if (null !== k) {
        var l = a.propertyKeys(f, b, h), m = a.valueByKey(f, c, h), n = a.propertyKeys(f, c, h), p = d.getItemKey(e, d.itemCount(e) - 1);
        l.forEach(function(c) {
          var e = a.getProperty(f, b, h, c);
          d.setItemProperty(f, p, c, e);
        });
        n.forEach(function(b) {
          var e = a.getProperty(f, c, h, b);
          d.setItemProperty(f, p, b, e);
        });
        d.add(f, k, m);
      }
    }
  }
  return d;
};
jsfc.HistogramDataset = function(a) {
  this._seriesKey = a;
  this._bins = [];
  this._selections = [];
  this._listeners = [];
};
jsfc.HistogramDataset.prototype.binCount = function() {
  return this._bins.length;
};
jsfc.HistogramDataset.prototype.isEmpty = function() {
  var a = !0;
  this._bins.forEach(function(b) {
    0 < b.count && (a = !1);
  });
  return a;
};
jsfc.HistogramDataset.prototype.addBin = function(a, b, c, d) {
  a = new jsfc.Bin(a, b, !1 !== c, !1 !== d);
  this._bins.push(a);
  return this;
};
jsfc.HistogramDataset.prototype.isOverlapping = function(a) {
  for (var b = 0;b < this._bins.length;b++) {
    if (this._bins[b].overlaps(a)) {
      return!0;
    }
  }
  return!1;
};
jsfc.HistogramDataset.prototype.binMid = function(a) {
  a = this._bins[a];
  return(a.xmin + a.xmax) / 2;
};
jsfc.HistogramDataset.prototype.binStart = function(a) {
  return this._bins[a].xmin;
};
jsfc.HistogramDataset.prototype.binEnd = function(a) {
  return this._bins[a].xmax;
};
jsfc.HistogramDataset.prototype.count = function(a) {
  return this._bins[a].count;
};
jsfc.HistogramDataset.prototype.reset = function() {
  this._bins.forEach(function(a) {
    a.count = 0;
  });
  return this;
};
jsfc.HistogramDataset.prototype._binIndex = function(a) {
  for (var b = 0;b < this._bins.length;b++) {
    if (this._bins[b].includes(a)) {
      return b;
    }
  }
  return-1;
};
jsfc.HistogramDataset.prototype.bounds = function() {
  for (var a = Number.POSITIVE_INFINITY, b = Number.NEGATIVE_INFINITY, c = 0, d = 0, e = 0;e < this.binCount();e++) {
    var f = this._bins[e], a = Math.min(a, f.xmin), b = Math.max(b, f.xmax), c = Math.min(c, f.y), d = Math.max(d, f.y)
  }
  return[a, b, c, d];
};
jsfc.HistogramDataset.prototype.add = function(a, b) {
  var c = this._binIndex(a);
  if (0 <= c) {
    this._bins[c].count++;
  } else {
    throw Error("No bin for the value " + a);
  }
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.HistogramDataset.prototype.addAll = function(a, b) {
  var c = this;
  a.forEach(function(a) {
    c.add(a, !1);
  });
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.HistogramDataset.prototype.seriesCount = function() {
  return 1;
};
jsfc.HistogramDataset.prototype.itemCount = function(a) {
  return this.binCount();
};
jsfc.HistogramDataset.prototype.xbounds = function() {
  for (var a = Number.POSITIVE_INFINITY, b = Number.NEGATIVE_INFINITY, c = 0;c < this.seriesCount();c++) {
    for (var d = 0;d < this.itemCount(c);d++) {
      var e = this.xStart(c, d), f = this.xEnd(c, d), a = Math.min(a, e), b = Math.max(b, f)
    }
  }
  return[a, b];
};
jsfc.HistogramDataset.prototype.ybounds = function() {
  for (var a = Number.POSITIVE_INFINITY, b = Number.NEGATIVE_INFINITY, c = 0;c < this.seriesCount();c++) {
    for (var d = 0;d < this.itemCount(c);d++) {
      var e = this.y(c, d), a = Math.min(a, e), b = Math.max(b, e)
    }
  }
  return[a, b];
};
jsfc.HistogramDataset.prototype.seriesKeys = function() {
  return[this._seriesKey];
};
jsfc.HistogramDataset.prototype.seriesIndex = function(a) {
  return a === this._seriesKey ? 0 : -1;
};
jsfc.HistogramDataset.prototype.seriesKey = function(a) {
  if (0 === a) {
    return this._seriesKey;
  }
  throw Error("Invalid seriesIndex: " + a);
};
jsfc.HistogramDataset.prototype.getItemKey = function(a, b) {
  if (0 === a) {
    return b;
  }
  throw Error("Invalid seriesIndex: " + a);
};
jsfc.HistogramDataset.prototype.itemIndex = function(a, b) {
  if (a === this._seriesKey) {
    return b;
  }
  throw Error("Invalid seriesIndex: " + a);
};
jsfc.HistogramDataset.prototype.x = function(a, b) {
  return this.binMid(b);
};
jsfc.HistogramDataset.prototype.xStart = function(a, b) {
  return this.binStart(b);
};
jsfc.HistogramDataset.prototype.xEnd = function(a, b) {
  return this.binEnd(b);
};
jsfc.HistogramDataset.prototype.y = function(a, b) {
  return this.count(b);
};
jsfc.HistogramDataset.prototype.getProperty = function(a, b, c) {
  return null;
};
jsfc.HistogramDataset.prototype.addListener = function(a) {
  this._listeners.push(a);
  return this;
};
jsfc.HistogramDataset.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.HistogramDataset.prototype.notifyListeners = function() {
  for (var a = 0;a < this._listeners.length;a++) {
    this._listeners[a].datasetChanged(this);
  }
  return this;
};
jsfc.KeyedValuesDataset = function() {
  if (!(this instanceof jsfc.KeyedValuesDataset)) {
    return new jsfc.KeyedValuesDataset;
  }
  this.data = {sections:[]};
  this.properties = [];
  this.selections = [];
  this._listeners = [];
};
jsfc.KeyedValuesDataset.prototype.itemCount = function() {
  return this.data.sections.length;
};
jsfc.KeyedValuesDataset.prototype.isEmpty = function() {
  return 0 === this.data.sections.length;
};
jsfc.KeyedValuesDataset.prototype.key = function(a) {
  return this.data.sections[a].key;
};
jsfc.KeyedValuesDataset.prototype.keys = function() {
  return this.data.sections.map(function(a) {
    return a.key;
  });
};
jsfc.KeyedValuesDataset.prototype.indexOf = function(a) {
  for (var b = this.data.sections.length, c = 0;c < b;c++) {
    if (this.data.sections[c].key === a) {
      return c;
    }
  }
  return-1;
};
jsfc.KeyedValuesDataset.prototype.valueByIndex = function(a) {
  return this.data.sections[a].value;
};
jsfc.KeyedValuesDataset.prototype.valueByKey = function(a) {
  a = this.indexOf(a);
  return 0 > a ? null : this.valueByIndex(a);
};
jsfc.KeyedValuesDataset.prototype.addListener = function(a) {
  this._listeners.push(a);
  return this;
};
jsfc.KeyedValuesDataset.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.KeyedValuesDataset.prototype.notifyListeners = function() {
  for (var a = 0;a < this._listeners.length;a++) {
    this._listeners[a].datasetChanged(this);
  }
  return this;
};
jsfc.KeyedValuesDataset.prototype.add = function(a, b, c) {
  var d = this.indexOf(a);
  0 > d ? (this.data.sections.push({key:a, value:b}), this.properties.push(new jsfc.Map)) : this.data.sections[d].value = b;
  !1 !== c && this.notifyListeners();
  return this;
};
jsfc.KeyedValuesDataset.prototype.remove = function(a, b) {
  if (!a) {
    throw Error("The 'sectionKey' must be defined.");
  }
  var c = this.indexOf(a);
  if (0 > c) {
    throw Error("The sectionKey '" + a.toString() + "' is not recognised.");
  }
  this.data.sections.splice(c, 1);
  this.properties.splice(c, 1);
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.KeyedValuesDataset.prototype.parse = function(a, b) {
  this.data.sections = JSON.parse(a);
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.KeyedValuesDataset.prototype.load = function(a, b) {
  this.data.sections = a;
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.KeyedValuesDataset.prototype.removeByIndex = function(a) {
  this.data.sections.splice(a, 1);
  this.properties.splice(a, 1);
  return this;
};
jsfc.KeyedValuesDataset.prototype.totalForDataset = function(a) {
  for (var b = 0, c = a.itemCount(), d = 0;d < c;d++) {
    var e = a.valueByIndex(d);
    e && (b += e);
  }
  return b;
};
jsfc.KeyedValuesDataset.prototype.minForDataset = function(a) {
  for (var b = Number.NaN, c = a.itemCount(), d = 0;d < c;d++) {
    var e = a.valueByIndex(d);
    e && (b = b ? Math.min(b, e) : e);
  }
  return b;
};
jsfc.KeyedValuesDataset.prototype.maxForDataset = function(a) {
  for (var b = Number.NaN, c = a.itemCount(), d = 0;d < c;d++) {
    var e = a.valueByIndex(d);
    e && (b = b ? Math.max(b, e) : e);
  }
  return b;
};
jsfc.KeyedValuesDataset.prototype.total = function() {
  return this.totalForDataset(this);
};
jsfc.KeyedValuesDataset.prototype.min = function() {
  return this.minForDataset(this);
};
jsfc.KeyedValuesDataset.prototype.max = function() {
  return this.maxForDataset(this);
};
jsfc.KeyedValuesDataset.prototype.propertyKeys = function(a) {
  a = this.indexOf(a);
  return(a = this.properties[a]) ? a.keys() : [];
};
jsfc.KeyedValuesDataset.prototype.getProperty = function(a, b) {
  var c = this.indexOf(a);
  return this.properties[c].get(b);
};
jsfc.KeyedValuesDataset.prototype.setProperty = function(a, b, c) {
  var d = this.indexOf(a);
  if (0 > d) {
    throw Error("Did not recognise 'sectionKey' " + a);
  }
  this.properties[d].put(b, c);
};
jsfc.KeyedValuesDataset.prototype.clearProperties = function(a) {
  a = this.indexOf(a);
  this.properties[a] = new jsfc.Map;
};
jsfc.KeyedValuesDataset.prototype.select = function(a, b) {
  var c;
  c = this._indexOfSelection(a);
  0 > c ? (c = {id:a, items:[]}, this.selections.push(c)) : c = this.selections[c];
  0 > c.items.indexOf(b) && c.items.push(b);
  return this;
};
jsfc.KeyedValuesDataset.prototype.unselect = function(a, b) {
  var c = this._indexOfSelection(a);
  if (0 <= c) {
    var c = this.selections[c], d = c.items.indexOf(b);
    0 <= d && c.items.splice(d, 1);
  }
  return this;
};
jsfc.KeyedValuesDataset.prototype.isSelected = function(a, b) {
  var c = this._indexOfSelection(a);
  return 0 > c ? !1 : 0 <= this.selections[c].items.indexOf(b);
};
jsfc.KeyedValuesDataset.prototype.clearSelection = function(a, b) {
  var c = this._indexOfSelection(a);
  0 <= c && this.selections.splice(c, 1);
  return this;
};
jsfc.KeyedValuesDataset.prototype._indexOfSelection = function(a) {
  return jsfc.Utils.findInArray(this.selections, function(b) {
    return b.id === a;
  });
};
jsfc.KeyedValues2DDataset = function() {
  if (!(this instanceof jsfc.KeyedValues2DDataset)) {
    return new jsfc.KeyedValues2DDataset;
  }
  this.data = {columnKeys:[], rows:[]};
  this.properties = {dataset:null, columns:[], rows:[]};
  this.selections = [];
  this._rowKeyToIndexMap = new jsfc.Map;
  this._columnKeyToIndexMap = new jsfc.Map;
  this._listeners = [];
};
jsfc.KeyedValues2DDataset.prototype.rowCount = function() {
  return this.data.rows.length;
};
jsfc.KeyedValues2DDataset.prototype.columnCount = function() {
  return this.data.columnKeys.length;
};
jsfc.KeyedValues2DDataset.prototype.isEmpty = function() {
  return this.data.hasOwnProperty("columnKeys") ? 0 === this.data.columnKeys.length && 0 === this.data.rows.length : !0;
};
jsfc.KeyedValues2DDataset.prototype.add = function(a, b, c, d) {
  if (this.isEmpty()) {
    return this.data.columnKeys.push(b), this._columnKeyToIndexMap.put(b, 0), this.data.rows.push({key:a, values:[c]}), this.properties.columns.push(null), this.properties.rows.push({key:a, rowProperties:null, maps:[null]}), this._rowKeyToIndexMap.put(a, 0), this;
  }
  var e = this.columnIndex(b);
  if (0 > e) {
    e = this.data.columnKeys.push(b);
    this._columnKeyToIndexMap.put(b, e - 1);
    this.properties.columns.push(null);
    e = this.data.rows.length;
    for (b = 0;b < e;b++) {
      this.data.rows[b].values.push(null), this.properties.rows[b].maps.push(null);
    }
    e = this.columnCount() - 1;
  }
  b = this.rowIndex(a);
  0 > b ? (b = Array(this.columnCount()), b[e] = c, e = this.data.rows.push({key:a, values:b}), this._rowKeyToIndexMap.put(a, e - 1), c = Array(this.columnCount()), this.properties.rows.push({key:a, maps:c})) : this.data.rows[b].values[e] = c;
  !1 !== d && this.notifyListeners();
  return this;
};
jsfc.KeyedValues2DDataset.prototype.parse = function(a, b) {
  this.load(JSON.parse(a));
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.KeyedValues2DDataset.prototype.load = function(a, b) {
  this.data = a;
  this.data.hasOwnProperty("rows") || (this.data.rows = []);
  this.data.hasOwnProperty("columnKeys") || (this.data.columnKeys = []);
  this._columnKeyToIndexMap = this._buildKeyMap(this.data.columnKeys);
  this._rowKeyToIndexMap = this._buildKeyMap(this.rowKeys());
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.KeyedValues2DDataset.prototype._buildKeyMap = function(a) {
  for (var b = new jsfc.Map, c = 0;c < a.length;c++) {
    b.put(a[c], c);
  }
  return b;
};
jsfc.KeyedValues2DDataset.prototype.valueByIndex = function(a, b) {
  return this.data.rows[a].values[b];
};
jsfc.KeyedValues2DDataset.prototype.rowKey = function(a) {
  return this.data.rows[a].key;
};
jsfc.KeyedValues2DDataset.prototype.rowIndex = function(a) {
  a = this._rowKeyToIndexMap.get(a);
  return void 0 !== a ? a : -1;
};
jsfc.KeyedValues2DDataset.prototype.rowKeys = function() {
  return this.data.rows.map(function(a) {
    return a.key;
  });
};
jsfc.KeyedValues2DDataset.prototype.columnKey = function(a) {
  return this.data.columnKeys[a];
};
jsfc.KeyedValues2DDataset.prototype.columnIndex = function(a) {
  a = this._columnKeyToIndexMap.get(a);
  return void 0 !== a ? a : -1;
};
jsfc.KeyedValues2DDataset.prototype.columnKeys = function() {
  return this.data.columnKeys.map(function(a) {
    return a;
  });
};
jsfc.KeyedValues2DDataset.prototype.valueByKey = function(a, b) {
  var c = this.rowIndex(a), d = this.columnIndex(b);
  return this.valueByIndex(c, d);
};
jsfc.KeyedValues2DDataset.prototype.getProperty = function(a) {
  var b = this.properties.dataset;
  if (b) {
    return b.get(a);
  }
};
jsfc.KeyedValues2DDataset.prototype.setProperty = function(a, b, c) {
  this.properties.dataset || (this.properties.dataset = new jsfc.Map);
  this.properties.dataset.put(a, b);
  !1 !== c && this.notifyListeners();
};
jsfc.KeyedValues2DDataset.prototype.getPropertyKeys = function() {
  return this.properties.dataset ? this.properties.dataset.keys() : [];
};
jsfc.KeyedValues2DDataset.prototype.clearProperties = function(a) {
  this.properties.dataset = null;
  !1 !== a && this.notifyListeners();
  return this;
};
jsfc.KeyedValues2DDataset.prototype.getRowPropertyKeys = function(a) {
  a = this.rowIndex(a);
  return(a = this.properties.rows[a].rowProperties) ? a.keys() : [];
};
jsfc.KeyedValues2DDataset.prototype.getRowProperty = function(a, b) {
  var c = this.rowIndex(a);
  if (c = this.properties.rows[c].rowProperties) {
    return c.get(b);
  }
};
jsfc.KeyedValues2DDataset.prototype.setRowProperty = function(a, b, c, d) {
  a = this.rowIndex(a);
  var e = this.properties.rows[a].rowProperties;
  e || (e = new jsfc.Map, this.properties.rows[a].rowProperties = e);
  e.put(b, c);
  !1 !== d && this.notifyListeners();
};
jsfc.KeyedValues2DDataset.prototype.clearRowProperties = function(a, b) {
  var c = this.rowIndex(a);
  this.properties.rows[c].rowProperties = null;
  !1 !== b && this.notifyListeners();
};
jsfc.KeyedValues2DDataset.prototype.getColumnPropertyKeys = function(a) {
  a = this.columnIndex(a);
  return(a = this.properties.columns[a]) ? a.keys() : [];
};
jsfc.KeyedValues2DDataset.prototype.getColumnProperty = function(a, b) {
  var c = this.columnIndex(a);
  if (c = this.properties.columns[c]) {
    return c.get(b);
  }
};
jsfc.KeyedValues2DDataset.prototype.setColumnProperty = function(a, b, c, d) {
  a = this.columnIndex(a);
  var e = this.properties.columns[a];
  e || (e = new jsfc.Map, this.properties.columns[a] = e);
  e.put(b, c);
  !1 !== d && this.notifyListeners();
};
jsfc.KeyedValues2DDataset.prototype.clearColumnProperties = function(a, b) {
  var c = this.columnIndex(a);
  this.properties.columns[c] = null;
  !1 !== b && this.notifyListeners();
};
jsfc.KeyedValues2DDataset.prototype.getItemProperty = function(a, b, c) {
  a = this.rowIndex(a);
  b = this.columnIndex(b);
  if (b = this.properties.rows[a][b]) {
    return b.get(c);
  }
};
jsfc.KeyedValues2DDataset.prototype.setItemProperty = function(a, b, c, d, e) {
  a = this.rowIndex(a);
  b = this.columnIndex(b);
  e = this.properties.rows[a][b];
  e || (e = new jsfc.Map, this.properties.rows[a][b] = e);
  e.put(c, d);
};
jsfc.KeyedValues2DDataset.prototype.getItemPropertyKeys = function(a, b) {
  var c = this.rowIndex(a), d = this.columnIndex(b);
  return(c = this.properties.rows[c][d]) ? c.keys() : [];
};
jsfc.KeyedValues2DDataset.prototype.clearItemProperties = function(a, b, c) {
  a = this.rowIndex(a);
  b = this.columnIndex(b);
  this.properties.rows[a][b] = null;
  !1 !== c && this.notifyListeners();
};
jsfc.KeyedValues2DDataset.prototype.select = function(a, b, c) {
  var d = this._indexOfSelection(a);
  0 > d ? (a = {id:a, items:[]}, this.selections.push(a)) : a = this.selections[d];
  0 > jsfc.Utils.findInArray(a.items, function(a) {
    return a.rowKey === b && a.columnKey === c;
  }) && a.items.push({rowKey:b, columnKey:c});
  return this;
};
jsfc.KeyedValues2DDataset.prototype.unselect = function(a, b, c) {
  a = this._indexOfSelection(a);
  if (0 <= a) {
    a = this.selections[a];
    var d = jsfc.Utils.findInArray(a.items, function(a, d) {
      return a.rowKey === b && a.columnKey === c;
    });
    0 <= d && a.items.splice(d, 1);
  }
  return this;
};
jsfc.KeyedValues2DDataset.prototype.isSelected = function(a, b, c) {
  a = this._indexOfSelection(a);
  return 0 > a ? !1 : 0 <= jsfc.Utils.findInArray(this.selections[a].items, function(a) {
    return a.rowKey === b && a.columnKey === c;
  });
};
jsfc.KeyedValues2DDataset.prototype.clearSelection = function(a) {
  a = this._indexOfSelection(a);
  0 <= a && this.selections.splice(a, 1);
  return this;
};
jsfc.KeyedValues2DDataset.prototype._indexOfSelection = function(a) {
  return jsfc.Utils.findInArray(this.selections, function(b) {
    return b.id === a;
  });
};
jsfc.KeyedValues2DDataset.prototype.addListener = function(a) {
  this._listeners.push(a);
  return this;
};
jsfc.KeyedValues2DDataset.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.KeyedValues2DDataset.prototype.notifyListeners = function() {
  for (var a = 0;a < this._listeners.length;a++) {
    this._listeners[a].datasetChanged(this);
  }
  return this;
};
jsfc.KeyedValues3DDataset = function() {
  if (!(this instanceof jsfc.KeyedValues3DDataset)) {
    return new jsfc.KeyedValues3DDataset;
  }
  this.data = {columnKeys:[], rowKeys:[], series:[]};
  this.properties = [];
  this._listeners = [];
};
jsfc.KeyedValues3DDataset.prototype.isEmpty = function() {
  return 0 === this.data.columnKeys.length && 0 === this.data.rowKeys.length;
};
jsfc.KeyedValues3DDataset.prototype.seriesCount = function() {
  return this.data.series.length;
};
jsfc.KeyedValues3DDataset.prototype.rowCount = function() {
  return this.data.rowKeys.length;
};
jsfc.KeyedValues3DDataset.prototype.columnCount = function() {
  return this.data.columnKeys.length;
};
jsfc.KeyedValues3DDataset.prototype._fetchRow = function(a, b) {
  for (var c = this.data.series[a].rows, d = 0;d < c.length;d++) {
    if (c[d].rowKey === b) {
      return c[d];
    }
  }
  return null;
};
jsfc.KeyedValues3DDataset.prototype.valueByIndex = function(a, b, c) {
  b = this.rowKey(b);
  a = this._fetchRow(a, b);
  return null === a ? null : a.values[c];
};
jsfc.KeyedValues3DDataset.prototype.seriesIndex = function(a) {
  for (var b = this.seriesCount(), c = 0;c < b;c++) {
    if (this.data.series[c].seriesKey === a) {
      return c;
    }
  }
  return-1;
};
jsfc.KeyedValues3DDataset.prototype.seriesKey = function(a) {
  return this.data.series[a].seriesKey;
};
jsfc.KeyedValues3DDataset.prototype.rowKey = function(a) {
  return this.data.rowKeys[a];
};
jsfc.KeyedValues3DDataset.prototype.rowIndex = function(a) {
  for (var b = this.data.rowKeys.length, c = 0;c < b;c++) {
    if (this.data.rowKeys[c] === a) {
      return c;
    }
  }
  return-1;
};
jsfc.KeyedValues3DDataset.prototype.rowKeys = function() {
  return this.data.rowKeys.map(function(a) {
    return a;
  });
};
jsfc.KeyedValues3DDataset.prototype.columnKey = function(a) {
  return this.data.columnKeys[a];
};
jsfc.KeyedValues3DDataset.prototype.columnIndex = function(a) {
  for (var b = this.data.columnKeys.length, c = 0;c < b;c++) {
    if (this.data.columnKeys[c] === a) {
      return c;
    }
  }
  return-1;
};
jsfc.KeyedValues3DDataset.prototype.columnKeys = function() {
  return this.data.columnKeys.map(function(a) {
    return a;
  });
};
jsfc.KeyedValues3DDataset.prototype.valueByKey = function(a, b, c) {
  a = this.seriesIndex(a);
  b = this._fetchRow(a, b);
  if (null === b) {
    return null;
  }
  c = this.columnIndex(c);
  return b.values[c];
};
jsfc.KeyedValues3DDataset.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.KeyedValues3DDataset.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
};
jsfc.KeyedValues3DDataset.prototype.notifyListeners = function() {
  for (var a = 0;a < this._listeners.length;a++) {
    this._listeners[a].datasetChanged(this);
  }
  return this;
};
jsfc.KeyedValues3DDataset.prototype.add = function(a, b, c, d) {
  if (this.isEmpty()) {
    this.data.rowKeys.push(b), this.data.columnKeys.push(c), this.data.series.push({seriesKey:a, rows:[{rowKey:b, values:[d]}]}), this.properties.push({seriesKey:a, rows:[{rowKey:b, maps:[null]}]});
  } else {
    var e = this.seriesIndex(a);
    0 > e && (this.data.series.push({seriesKey:a, rows:[]}), this.properties.push({seriesKey:a, rows:[]}), e = this.data.series.length - 1);
    a = this.columnIndex(c);
    if (0 > a) {
      this.data.columnKeys.push(c);
      for (a = 0;a < this.data.series.length;a++) {
        c = this.data.series[a].rows;
        for (var f = 0;f < c.length;f++) {
          c[f].values.push(null);
        }
      }
      for (a = 0;a < this.properties.length;a++) {
        for (c = this.properties[a].rows, f = 0;f < c.length;f++) {
          c[f].maps.push(null);
        }
      }
      a = this.columnCount() - 1;
    }
    0 > this.rowIndex(b) ? (this.data.rowKeys.push(b), c = jsfc.Utils.makeArrayOf(null, this.columnCount()), c[a] = d, this.data.series[e].rows.push({rowKey:b, values:c}), d = jsfc.Utils.makeArrayOf(null, this.columnCount()), this.properties[e].rows.push({rowKey:b, maps:d})) : (c = this._fetchRow(e, b), null !== c ? c.values[a] = d : (c = jsfc.Utils.makeArrayOf(null, this.columnCount()), c[a] = d, this.data.series[e].rows.push({rowKey:b, values:c})), null === this._fetchPropertyRow(e, b) && (d = 
    jsfc.Utils.makeArrayOf(null, this.columnCount()), this.properties[e].rows.push({rowKey:b, maps:d})));
  }
  return this;
};
jsfc.KeyedValues3DDataset.prototype.parse = function(a) {
  this.load(JSON.parse(a));
  return this;
};
jsfc.KeyedValues3DDataset.prototype.load = function(a) {
  this.data = a;
  this.data.hasOwnProperty("rowKeys") || (this.data.rowKeys = []);
  this.data.hasOwnProperty("columnKeys") || (this.data.columnKeys = []);
  this.data.hasOwnProperty("series") || (this.data.series = []);
  this.clearAllProperties();
  this.notifyListeners();
  return this;
};
jsfc.KeyedValues3DDataset.prototype.getProperty = function(a, b, c, d) {
  a = this.seriesIndex(a);
  b = this.rowIndex(b);
  c = this.columnIndex(c);
  if (c = this.properties[a].rows[b][c]) {
    return c.get(d);
  }
};
jsfc.KeyedValues3DDataset.prototype.setProperty = function(a, b, c, d, e) {
  a = this.seriesIndex(a);
  b = this.rowIndex(b);
  c = this.columnIndex(c);
  var f = this.properties[a].rows[b][c];
  f || (f = new jsfc.Map, this.properties[a].rows[b][c] = f);
  f.put(d, e);
};
jsfc.KeyedValues3DDataset.prototype.propertyKeys = function(a, b, c) {
  a = this.seriesIndex(a);
  b = this.rowIndex(b);
  c = this.columnIndex(c);
  return(c = this.properties[a].rows[b][c]) ? c.keys() : [];
};
jsfc.KeyedValues3DDataset.prototype.clearProperties = function(a, b, c) {
  a = this.seriesIndex(a);
  if (b = this._fetchPropertyRow(a, b)) {
    c = this.columnIndex(c), b[c] = null;
  }
};
jsfc.KeyedValues3DDataset.prototype.clearAllProperties = function() {
  this.properties = [];
  var a = this;
  this.data.series.forEach(function(b) {
    var c = {seriesKey:b.seriesKey, rows:[]};
    a.properties.push(c);
    b.rows.forEach(function(b) {
      var e = jsfc.Utils.makeArrayOf(null, a.columnCount());
      c.rows.push({rowKey:b.rowKey, maps:e});
    });
  });
  return this;
};
jsfc.KeyedValues3DDataset.prototype._fetchPropertyRow = function(a, b) {
  for (var c = this.properties[a].rows, d = 0;d < c.length;d++) {
    if (c[d].rowKey === b) {
      return c[d];
    }
  }
  return null;
};
jsfc.Map = function() {
  this._data = {};
};
jsfc.Map.prototype._escapeKey = function(a) {
  return 0 === a.indexOf("__proto__") ? a + "%" : a;
};
jsfc.Map.prototype.contains = function(a) {
  return Object.prototype.hasOwnProperty.call(this._data, a);
};
jsfc.Map.prototype.keys = function() {
  return Object.keys(this._data);
};
jsfc.Map.prototype.put = function(a, b) {
  a = this._escapeKey(a);
  this._data[a] = b;
};
jsfc.Map.prototype.get = function(a) {
  a = this._escapeKey(a);
  return this._getOwnPropertyValue(this._data, a);
};
jsfc.Map.prototype._getOwnPropertyValue = function(a, b) {
  return Object.prototype.hasOwnProperty.call(a, b) ? a[b] : void 0;
};
jsfc.Map.prototype.remove = function(a) {
  a = this._escapeKey(a);
  var b = this._getOwnPropertyValue(this._data, a);
  delete this._data[a];
  return b;
};
jsfc.Range = function(a, b) {
  if (a >= b) {
    throw Error("Requires lowerBound to be less than upperBound: " + a + ", " + b);
  }
  this._lowerBound = a;
  this._upperBound = b;
};
jsfc.Range.prototype.lowerBound = function() {
  return this._lowerBound;
};
jsfc.Range.prototype.upperBound = function() {
  return this._upperBound;
};
jsfc.Range.prototype.length = function() {
  return this._upperBound - this._lowerBound;
};
jsfc.Range.prototype.percent = function(a) {
  return(a - this._lowerBound) / this.length();
};
jsfc.Range.prototype.value = function(a) {
  return this._lowerBound + a * this.length();
};
jsfc.Range.prototype.contains = function(a) {
  return a >= this._lowerBound && a <= this._upperBound;
};
jsfc.Range.prototype.toString = function() {
  return "[Range: " + this._lowerBound + ", " + this._upperBound + "]";
};
jsfc.Values2DDatasetUtils = {};
jsfc.Values2DDatasetUtils.ybounds = function(a, b) {
  for (var c = b ? b : Number.POSITIVE_INFINITY, d = b ? b : Number.NEGATIVE_INFINITY, e = 0;e < a.rowCount();e++) {
    for (var f = 0;f < a.columnCount();f++) {
      var g = a.valueByIndex(e, f);
      g && (c = Math.min(c, g), d = Math.max(d, g));
    }
  }
  return[c, d];
};
jsfc.Values2DDatasetUtils.stackBaseY = function(a, b, c, d) {
  var e = a.valueByIndex(b, c);
  d = d || 0;
  if (0 < e) {
    for (var f = 0;f < b;f++) {
      e = a.valueByIndex(f, c), 0 < e && (d += e);
    }
  } else {
    if (0 > e) {
      for (f = 0;f < b;f++) {
        e = a.valueByIndex(f, c), 0 > e && (d += e);
      }
    }
  }
  return d;
};
jsfc.Values2DDatasetUtils.stackYBounds = function(a, b) {
  for (var c = b ? b : 0, d = b ? b : 0, e = 0;e < a.columnCount();e++) {
    for (var f = b || 0, g = b || 0, h = 0;h < a.rowCount();h++) {
      var k = a.valueByIndex(h, e);
      0 < k ? f += k : 0 > k && (g += k);
      c = Math.min(c, g);
      d = Math.max(d, f);
    }
  }
  return[c, d];
};
jsfc.XYZDataset = function() {
  this.data = {series:[]};
  this.properties = [];
  this.selections = [];
  this._listeners = [];
};
jsfc.XYZDataset.prototype.seriesCount = function() {
  return this.data.series.length;
};
jsfc.XYZDataset.prototype.seriesKeys = function() {
  return this.data.series.map(function(a) {
    return a.seriesKey;
  });
};
jsfc.XYZDataset.prototype.seriesKey = function(a) {
  return this.data.series[a].seriesKey;
};
jsfc.XYZDataset.prototype.seriesIndex = function(a) {
  jsfc.Args.requireString(a, "seriesKey");
  for (var b = this.data.series, c = this.data.series.length, d = 0;d < c;d++) {
    if (b[d].seriesKey === a) {
      return d;
    }
  }
  return-1;
};
jsfc.XYZDataset.prototype.itemCount = function(a) {
  return this.data.series[a].items.length;
};
jsfc.XYZDataset.prototype.itemIndex = function(a, b) {
  jsfc.Args.require(b, "itemKey");
  for (var c = this.seriesIndex(a), c = this.data.series[c].items, d = 0;d < c.length;d++) {
    if (c[d].key === b) {
      return d;
    }
  }
  return-1;
};
jsfc.XYZDataset.prototype.x = function(a, b) {
  return this.data.series[a].items[b].x;
};
jsfc.XYZDataset.prototype.y = function(a, b) {
  return this.data.series[a].items[b].y;
};
jsfc.XYZDataset.prototype.z = function(a, b) {
  return this.data.series[a].items[b].z;
};
jsfc.XYZDataset.prototype.item = function(a, b) {
  return this.data.series[a].items[b];
};
jsfc.XYZDataset.prototype.itemByKey = function(a, b) {
  for (var c = this.seriesIndex(a), c = this.data.series[c].items, d = 0;d < c.length;d++) {
    if (c[d].key === b) {
      return c[d];
    }
  }
  return null;
};
jsfc.XYZDataset.prototype.getItemKey = function(a, b) {
  return this.item(a, b).key;
};
jsfc.XYZDataset.prototype.generateItemKey = function(a) {
  if (0 > a) {
    return 0;
  }
  var b = 0, c = Number.MIN_VALUE;
  a = this.data.series[a].items;
  for (var d = 0;d < a.length;d++) {
    "number" === typeof a[d].key && (c = Math.max(a[d].key, c)), b === a[d].key && (b = c + 1);
  }
  return b;
};
jsfc.XYZDataset.prototype.add = function(a, b, c, d, e) {
  jsfc.Args.requireNumber(b, "x");
  var f = this.generateItemKey(this.seriesIndex(a));
  return this.addByKey(a, f, b, c, d, e);
};
jsfc.XYZDataset.prototype.addByKey = function(a, b, c, d, e, f) {
  jsfc.Args.requireString(a, "seriesKey");
  var g = this.seriesIndex(a);
  0 > g && (this.addSeries(a), g = this.data.series.length - 1);
  (a = this.itemByKey(a, b)) ? (a.x = c, a.y = d) : (this.data.series[g].items.push({x:c, y:d, z:e, key:b}), this.properties[g].maps.push(null));
  !1 !== f && this.notifyListeners();
  return this;
};
jsfc.XYZDataset.prototype.addSeries = function(a) {
  jsfc.Args.requireString(a, "seriesKey");
  if (0 <= this.seriesIndex(a)) {
    throw Error("There is already a series with the key '" + a);
  }
  this.data.series.push({seriesKey:a, items:[]});
  this.properties.push({seriesKey:a, maps:[]});
  return this;
};
jsfc.XYZDataset.prototype.removeSeries = function(a) {
  jsfc.Args.requireString(a, "seriesKey");
  a = this.seriesIndex(a);
  0 <= a && this.data.series.splice(a, 1);
  return this;
};
jsfc.XYZDataset.prototype.addListener = function(a) {
  this._listeners.push(a);
  return this;
};
jsfc.XYZDataset.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.XYZDataset.prototype.notifyListeners = function() {
  for (var a = 0;a < this._listeners.length;a++) {
    this._listeners[a](this);
  }
  return this;
};
jsfc.XYZDataset.prototype.getProperty = function(a, b, c) {
  var d = this.seriesIndex(a);
  a = this.itemIndex(a, b);
  if (d = this.properties[d].maps[a]) {
    return d.get(c);
  }
};
jsfc.XYZDataset.prototype.setProperty = function(a, b, c, d) {
  var e = this.seriesIndex(a);
  a = this.itemIndex(a, b);
  b = this.properties[e][a];
  b || (b = new jsfc.Map, this.properties[e].maps[a] = b);
  b.put(c, d);
};
jsfc.XYZDataset.prototype.clearProperties = function(a, b) {
  var c = this.seriesIndex(a), d = this.itemIndex(a, b);
  this.properties[c].maps[d] = null;
};
jsfc.XYZDataset.prototype.select = function(a, b, c) {
  var d = this._indexOfSelection(a);
  0 > d ? (a = {id:a, items:[]}, this.selections.push(a)) : a = this.selections[d];
  0 > jsfc.Utils.findInArray(a.items, function(a) {
    return a.seriesKey === b && a.item === c;
  }) && a.items.push({seriesKey:b, item:c});
  return this;
};
jsfc.XYZDataset.prototype.unselect = function(a, b, c) {
  a = this._indexOfSelection(a);
  if (0 <= a) {
    a = this.selections[a];
    var d = jsfc.Utils.findInArray(a.items, function(a, d) {
      return a.seriesKey === b && a.item === c;
    });
    0 <= d && a.items.splice(d, 1);
  }
  return this;
};
jsfc.XYZDataset.prototype.isSelected = function(a, b, c) {
  a = this._indexOfSelection(a);
  return 0 > a ? !1 : 0 <= jsfc.Utils.findInArray(this.selections[a].items, function(a) {
    return a.seriesKey === b && a.item === c;
  });
};
jsfc.XYZDataset.prototype.clearSelection = function(a) {
  a = this._indexOfSelection(a);
  0 <= a && this.selections.splice(a, 1);
};
jsfc.XYZDataset.prototype._indexOfSelection = function(a) {
  return jsfc.Utils.findInArray(this.selections, function(b) {
    return b.id === a;
  });
};
jsfc.Charts = {};
jsfc.Charts.createTitleElement = function(a, b, c) {
  jsfc.Args.requireString(a, "title");
  var d = new jsfc.Font("Palatino, serif", 16, !0, !1), e = jsfc.HAlign.LEFT;
  c = c ? c.refPt() : jsfc.RefPt2D.TOP_LEFT;
  jsfc.RefPt2D.isHorizontalCenter(c) ? e = jsfc.HAlign.CENTER : jsfc.RefPt2D.isRight(c) && (e = jsfc.HAlign.RIGHT);
  a = new jsfc.TextElement(a);
  a.setFont(d);
  a.halign(e);
  a.isTitle = !0;
  return b ? (d = new jsfc.Font("Palatino, serif", 12, !1, !0), b = new jsfc.TextElement(b), b.setFont(d), b.halign(e), b.isSubtitle = !0, e = new jsfc.GridElement, e.setInsets(new jsfc.Insets(0, 0, 0, 0)), e.add(a, "R1", "C1"), e.add(b, "R2", "C1"), e) : a;
};
jsfc.Charts.createBarChart = function(a, b, c, d, e) {
  c = new jsfc.CategoryPlot(c);
  var f = new jsfc.BarRenderer(c);
  c.setRenderer(f);
  c.getXAxis().setLabel(d || "Category");
  d = c.getYAxis();
  d.setLabel(e || "Value");
  d.setAutoRangeIncludesZero(!0);
  return(new jsfc.Chart(c)).setTitle(a, b);
};
jsfc.Charts.createStackedBarChart = function(a, b, c, d, e) {
  c = new jsfc.CategoryPlot(c);
  c.setRenderer(new jsfc.StackedBarRenderer(c));
  c.getXAxis().setLabel(d || "Category");
  d = c.getYAxis();
  d.setLabel(e || "Value");
  d.setAutoRangeIncludesZero(!0);
  return(new jsfc.Chart(c)).setTitle(a, b);
};
jsfc.Charts.createLineChart = function(a, b, c, d, e) {
};
jsfc.Charts.createScatterChart = function(a, b, c, d, e) {
  jsfc.Args.requireXYDataset(c, "dataset");
  c = new jsfc.XYPlot(c);
  c.getXAxis().setLabel(d);
  c.getYAxis().setLabel(e);
  c.setRenderer(new jsfc.ScatterRenderer(c));
  d = new jsfc.Chart(c);
  d.setPadding(5, 5, 5, 5);
  d.setTitle(a, b, d.getTitleAnchor());
  return d;
};
jsfc.Charts.createXYLineChart = function(a, b, c, d, e) {
  jsfc.Args.requireXYDataset(c, "dataset");
  c = new jsfc.XYPlot(c);
  c.getXAxis().setLabel(d);
  c.getYAxis().setLabel(e);
  d = new jsfc.XYLineRenderer;
  e = new jsfc.Chart(c);
  e.setTitleElement(jsfc.Charts.createTitleElement(a, b, e.getTitleAnchor()));
  c.setRenderer(d);
  return e;
};
jsfc.Charts.createXYBarChart = function(a, b, c, d, e) {
  c = new jsfc.XYPlot(c);
  c.getXAxis().setLabel(d);
  c.getYAxis().setLabel(e);
  d = new jsfc.XYBarRenderer;
  c.setRenderer(d);
  d = new jsfc.Chart(c);
  e = new jsfc.Anchor2D(jsfc.RefPt2D.TOP_LEFT);
  d.setTitle(a, b, e);
  return d;
};
jsfc.Chart = function(a) {
  if (!(this instanceof jsfc.Chart)) {
    throw Error("Use 'new' for construction.");
  }
  this._size = new jsfc.Dimension(400, 240);
  var b = new jsfc.Color(255, 255, 255);
  this._backgroundPainter = new jsfc.StandardRectanglePainter(b, null);
  this._padding = new jsfc.Insets(4, 4, 4, 4);
  this._titleElement = null;
  this._titleAnchor = new jsfc.Anchor2D(jsfc.RefPt2D.TOP_LEFT);
  this._plot = a;
  this._legendBuilder = new jsfc.StandardLegendBuilder;
  this._legendAnchor = new jsfc.Anchor2D(jsfc.RefPt2D.BOTTOM_RIGHT);
  this._listeners = [];
  b = function(a) {
    return function(b) {
      a.notifyListeners();
    };
  }(this);
  a.addListener(b);
  a.chart = this;
};
jsfc.Chart.prototype.getElementID = function() {
  return this._elementId;
};
jsfc.Chart.prototype.setElementID = function(a) {
  this._elementId = a;
};
jsfc.Chart.prototype.getSize = function() {
  return this._size;
};
jsfc.Chart.prototype.setSize = function(a, b, c) {
  this._size = new jsfc.Dimension(a, b);
  !1 !== c && this.notifyListeners();
};
jsfc.Chart.prototype.getBackground = function() {
  return this._backgroundPainter;
};
jsfc.Chart.prototype.setBackground = function(a, b) {
  this._backgroundPainter = a;
  !1 !== b && this.notifyListeners();
};
jsfc.Chart.prototype.setBackgroundColor = function(a, b) {
  var c = new jsfc.StandardRectanglePainter(a);
  this.setBackground(c, b);
};
jsfc.Chart.prototype.getPadding = function() {
  return this._padding;
};
jsfc.Chart.prototype.setPadding = function(a, b, c, d, e) {
  this._padding = new jsfc.Insets(a, b, c, d);
  !1 !== e && this.notifyListeners();
};
jsfc.Chart.prototype.getTitleElement = function() {
  return this._titleElement;
};
jsfc.Chart.prototype.setTitleElement = function(a, b) {
  this._titleElement = a;
  !1 !== b && this.notifyListeners();
};
jsfc.Chart.prototype.setTitle = function(a, b, c, d) {
  a = jsfc.Charts.createTitleElement(a, b, c);
  this.setTitleElement(a, d);
  return this;
};
jsfc.Chart.prototype.updateTitle = function(a, b, c) {
  this._titleElement && this._titleElement.receive(function(d) {
    d instanceof jsfc.TextElement && d.isTitle && (a && d.setText(a), b && d.setFont(b), c && d.setColor(c));
  });
};
jsfc.Chart.prototype.updateSubtitle = function(a, b, c) {
  this._titleElement && this._titleElement.receive(function(d) {
    d instanceof jsfc.TextElement && d.isSubtitle && (a && d.setText(a), b && d.setFont(b), c && d.setColor(c));
  });
};
jsfc.Chart.prototype.getTitleAnchor = function() {
  return this._titleAnchor;
};
jsfc.Chart.prototype.setTitleAnchor = function(a, b) {
  this._titleAnchor = a;
  !1 !== b && this.notifyListeners();
};
jsfc.Chart.prototype.getPlot = function() {
  return this._plot;
};
jsfc.Chart.prototype.getLegendBuilder = function() {
  return this._legendBuilder;
};
jsfc.Chart.prototype.setLegendBuilder = function(a, b) {
  this._legendBuilder = a;
  !1 !== b && this.notifyListeners();
};
jsfc.Chart.prototype.getLegendAnchor = function() {
  return this._legendAnchor;
};
jsfc.Chart.prototype.setLegendAnchor = function(a, b) {
  this._legendAnchor = a;
  !1 !== b && this.notifyListeners();
};
jsfc.Chart.prototype._adjustMargin = function(a, b, c) {
  jsfc.RefPt2D.isTop(c.refPt()) ? a.top += b.height() : jsfc.RefPt2D.isBottom(c.refPt()) && (a.bottom += b.height());
};
jsfc.Chart.prototype.draw = function(a, b) {
  this._backgroundPainter && this._backgroundPainter.paint(a, b);
  var c = new jsfc.Dimension(0, 0), d = new jsfc.Dimension(0, 0);
  this._titleElement && (c = this._titleElement.preferredSize(a, b));
  var e;
  this._legendBuilder && (e = this._legendBuilder.createLegend(this._plot, this._legendAnchor, "orientation", {}), d = e.preferredSize(a, b));
  var f = this.getPadding(), g = f.left(), h = f.top() + c.height(), k = this._size.width() - f.left() - f.right(), f = this._size.height() - f.top() - f.bottom() - c.height() - d.height();
  this._plotArea = new jsfc.Rectangle(g, h, k, f);
  this._plot.draw(a, b, this._plotArea);
  e && (g = new jsfc.Fit2D(this._legendAnchor), d = g.fit(d, b), e.draw(a, d));
  this._titleElement && (g = new jsfc.Fit2D(this._titleAnchor), d = g.fit(c, b), this._titleElement.draw(a, d));
};
jsfc.Chart.prototype.plotArea = function() {
  return this._plotArea;
};
jsfc.Chart.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.Chart.prototype.notifyListeners = function() {
  var a = this;
  this._listeners.forEach(function(b) {
    b(a);
  });
};
jsfc.ChartManager = function(a, b, c, d, e) {
  if (!(this instanceof jsfc.ChartManager)) {
    throw Error("Use 'new' for constructor.");
  }
  this._element = a;
  this._chart = b;
  this._ctx = "CANVAS" === a.nodeName ? new jsfc.CanvasContext2D(a) : new jsfc.SVGContext2D(a);
  a = function(a) {
    return function(b) {
      a.refreshDisplay();
    };
  }(this);
  b.addListener(a);
  this._liveMouseHandler = null;
  this._availableLiveMouseHandlers = [];
  !1 !== c && (b = new jsfc.Modifier(!1, !1, !1, !1), b = new jsfc.ZoomHandler(this, b), this._availableLiveMouseHandlers.push(b));
  !1 !== e && (e = new jsfc.Modifier(!0, !1, !1, !1), e = new jsfc.PanHandler(this, e), this._availableLiveMouseHandlers.push(e));
  this._auxiliaryMouseHandlers = [];
  !1 !== d && this._auxiliaryMouseHandlers.push(new jsfc.WheelHandler(this));
  this.installMouseDownHandler(this._element);
  this.installMouseMoveHandler(this._element);
  this.installMouseUpHandler(this._element);
  this.installMouseOverHandler(this._element);
  this.installMouseOutHandler(this._element);
  this.installMouseWheelHandler(this._element);
};
jsfc.ChartManager.prototype.getChart = function() {
  return this._chart;
};
jsfc.ChartManager.prototype.getElement = function() {
  return this._element;
};
jsfc.ChartManager.prototype.getContext = function() {
  return this._ctx;
};
jsfc.ChartManager.prototype.addLiveHandler = function(a) {
  this._availableLiveMouseHandlers.push(a);
};
jsfc.ChartManager.prototype.removeLiveHandler = function(a) {
  var b = jsfc.Utils.findItemInArray(a, this._availableLiveMouseHandlers);
  -1 !== b && (a.cleanUp(), this._availableLiveMouseHandlers.splice(b, 1));
};
jsfc.ChartManager.prototype.addAuxiliaryHandler = function(a) {
  this._auxiliaryMouseHandlers.push(a);
};
jsfc.ChartManager.prototype.removeAuxiliaryHandler = function(a) {
  var b = jsfc.Utils.findItemInArray(a, this._auxiliaryMouseHandlers);
  -1 !== b && (a.cleanUp(), this._auxiliaryMouseHandlers.splice(b, 1));
};
jsfc.ChartManager.prototype.refreshDisplay = function() {
  var a = this._chart.getSize(), a = new jsfc.Rectangle(0, 0, a.width(), a.height());
  this._ctx.clear();
  this._chart.draw(this._ctx, a);
};
jsfc.ChartManager.prototype._matchLiveHandler = function(a, b, c, d) {
  for (var e = this._availableLiveMouseHandlers, f = 0;f < e.length;f++) {
    var g = e[f];
    if (g.getModifier().match(a, b, c, d)) {
      return g;
    }
  }
  return null;
};
jsfc.ChartManager.prototype.installMouseDownHandler = function(a) {
  var b = this;
  a.onmousedown = function(a) {
    if (null !== b._liveMouseHandler) {
      b._liveMouseHandler.mouseDown(a);
    } else {
      var d = b._matchLiveHandler(a.altKey, a.ctrlKey, a.metaKey, a.shiftKey);
      d && (b._liveMouseHandler = d, b._liveMouseHandler.mouseDown(a));
    }
    b._auxiliaryMouseHandlers.forEach(function(b) {
      b.mouseDown(a);
    });
    a.preventDefault();
  };
};
jsfc.ChartManager.prototype.installMouseMoveHandler = function(a) {
  var b = this;
  a.onmousemove = function(a) {
    null !== b._liveMouseHandler && b._liveMouseHandler.mouseMove(a);
    b._auxiliaryMouseHandlers.forEach(function(b) {
      b.mouseMove(a);
    });
    a.stopPropagation();
    return!1;
  };
};
jsfc.ChartManager.prototype.installMouseUpHandler = function(a) {
  var b = this;
  a.onmouseup = function(a) {
    null !== b._liveMouseHandler && b._liveMouseHandler.mouseUp(a);
    b._auxiliaryMouseHandlers.forEach(function(b) {
      b.mouseUp(a);
    });
  };
};
jsfc.ChartManager.prototype.installMouseOverHandler = function(a) {
  var b = this;
  a.onmouseover = function(a) {
    null !== b._liveMouseHandler && b._liveMouseHandler.mouseOver(a);
    b._auxiliaryMouseHandlers.forEach(function(b) {
      b.mouseOver(a);
    });
  };
};
jsfc.ChartManager.prototype.installMouseOutHandler = function(a) {
  var b = this;
  a.onmouseout = function(a) {
    null !== b._liveMouseHandler && b._liveMouseHandler.mouseOut(a);
    b._auxiliaryMouseHandlers.forEach(function(b) {
      b.mouseOut(a);
    });
  };
};
jsfc.ChartManager.prototype.installMouseWheelHandler = function(a) {
  var b = this, c = /Firefox/i.test(navigator.userAgent) ? "DOMMouseScroll" : "mousewheel";
  a.addEventListener(c, function(a) {
    var c = !0;
    null !== b._liveMouseHandler && (c = b._liveMouseHandler.mouseWheel(a));
    b._auxiliaryMouseHandlers.forEach(function(b) {
      c = b.mouseWheel(a) && c;
    });
    return c;
  }, !1);
};
jsfc.LegendBuilder = function() {
};
jsfc.LegendBuilder.prototype.createLegend = function(a, b, c, d) {
};
jsfc.StandardLegendBuilder = function(a) {
  if (!(this instanceof jsfc.StandardLegendBuilder)) {
    throw Error("Use 'new' for constructor.");
  }
  a || (a = this);
  jsfc.StandardLegendBuilder.init(a);
};
jsfc.StandardLegendBuilder.init = function(a) {
  a._font = new jsfc.Font("Palatino, serif", 12);
};
jsfc.StandardLegendBuilder.prototype.getFont = function() {
  return this._font;
};
jsfc.StandardLegendBuilder.prototype.setFont = function(a) {
  this._font = a;
};
jsfc.StandardLegendBuilder.prototype.createLegend = function(a, b, c, d) {
  a = a.legendInfo();
  var e = new jsfc.FlowElement, f = this;
  a.forEach(function(a) {
    var b = (new jsfc.RectangleElement(8, 5)).setFillColor(a.color);
    a = (new jsfc.TextElement(a.label)).setFont(f._font);
    var c = new jsfc.GridElement;
    c.add(b, "R1", "C1");
    c.add(a, "R1", "C2");
    e.add(c);
  });
  return e;
};
jsfc.FixedLegendBuilder = function() {
  if (!(this instanceof jsfc.FixedLegendBuilder)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.StandardLegendBuilder.init(this);
  this._info = [];
};
jsfc.FixedLegendBuilder.prototype = new jsfc.StandardLegendBuilder;
jsfc.FixedLegendBuilder.prototype.add = function(a, b) {
  this._info.push(new jsfc.LegendItemInfo(a, b));
};
jsfc.FixedLegendBuilder.prototype.clear = function() {
  this._info = [];
};
jsfc.FixedLegendBuilder.prototype.createLegend = function(a, b, c, d) {
  a = this._info;
  var e = new jsfc.FlowElement, f = this;
  a.forEach(function(a) {
    var b = (new jsfc.RectangleElement(8, 5)).setFillColor(a.color);
    a = (new jsfc.TextElement(a.label)).setFont(f.getFont());
    var c = new jsfc.GridElement;
    c.add(b, "R1", "C1");
    c.add(a, "R1", "C2");
    e.add(c);
  });
  return e;
};
jsfc.LegendItemInfo = function(a, b) {
  this.seriesKey = a || "";
  this.label = a || "";
  this.description = "";
  this.shape = null;
  this.color = b;
};
jsfc.ValueAxis = function() {
  throw Error("This object documents an interface.");
};
jsfc.ValueAxis.prototype.setLabel = function(a, b) {
};
jsfc.ValueAxis.prototype.getLowerBound = function() {
};
jsfc.ValueAxis.prototype.getUpperBound = function() {
};
jsfc.ValueAxis.prototype.length = function() {
};
jsfc.ValueAxis.prototype.contains = function(a) {
};
jsfc.ValueAxis.prototype.configureAsXAxis = function(a) {
};
jsfc.ValueAxis.prototype.configureAsYAxis = function(a) {
};
jsfc.ValueAxis.prototype.valueToCoordinate = function(a, b, c) {
};
jsfc.ValueAxis.prototype.coordinateToValue = function(a, b, c) {
};
jsfc.ValueAxis.prototype.resizeRange = function(a, b, c) {
};
jsfc.ValueAxis.prototype.pan = function(a, b) {
};
jsfc.ValueAxis.prototype.reserveSpace = function(a, b, c, d, e) {
};
jsfc.ValueAxis.prototype.draw = function(a, b, c, d, e) {
};
jsfc.ValueAxis.prototype.addListener = function(a) {
};
jsfc.CategoryAxis = function() {
  throw Error("This object documents an interface.");
};
jsfc.CategoryAxis.prototype.setLabel = function(a, b) {
};
jsfc.CategoryAxis.prototype.configureAsXAxis = function(a) {
};
jsfc.CategoryAxis.prototype.keyToRange = function(a, b, c) {
};
jsfc.CategoryAxis.prototype.itemRange = function(a, b, c, d, e) {
};
jsfc.CategoryAxis.prototype.coordinateToKey = function(a, b, c) {
};
jsfc.CategoryAxis.prototype.reserveSpace = function(a, b, c, d, e) {
};
jsfc.CategoryAxis.prototype.draw = function(a, b, c, d, e) {
};
jsfc.CategoryAxis.prototype.addListener = function(a) {
};
jsfc.StandardCategoryAxis = function(a) {
  if (!(this instanceof jsfc.StandardCategoryAxis)) {
    throw Error("Use 'new' with constructor.");
  }
  this._label = a;
  this._labelFont = new jsfc.Font("Palatino;serif", 12, !0, !1);
  this._labelColor = new jsfc.Color(0, 0, 0);
  this._labelMargin = new jsfc.Insets(2, 2, 2, 2);
  this._tickLabelMargin = new jsfc.Insets(2, 2, 2, 2);
  this._tickLabelFont = new jsfc.Font("Palatino;serif", 12);
  this._tickLabelColor = new jsfc.Color(100, 100, 100);
  this._tickLabelFactor = 1.4;
  this._tickLabelOrientation = null;
  this._tickMarkOuterLength = this._tickMarkInnerLength = 2;
  this._tickMarkStroke = new jsfc.Stroke(0.5);
  this._tickMarkColor = new jsfc.Color(100, 100, 100);
  this._axisLineColor = new jsfc.Color(100, 100, 100);
  this._axisLineStroke = new jsfc.Stroke(0.5);
  this._upperMargin = this._lowerMargin = 0.05;
  this._categoryMargin = 0.1;
  this._categories = [];
  this._listeners = [];
};
jsfc.StandardCategoryAxis.prototype.getLabel = function() {
  return this._label;
};
jsfc.StandardCategoryAxis.prototype.setLabel = function(a, b) {
  this._label = a;
  !1 !== b && this.notifyListeners();
};
jsfc.StandardCategoryAxis.prototype.configureAsXAxis = function(a) {
  if (a = a.getDataset()) {
    this._categories = a.columnKeys();
  }
};
jsfc.StandardCategoryAxis.prototype._resolveTickLabelOrientation = function(a) {
  var b = this._tickLabelOrientation;
  if (!b) {
    if (a === jsfc.RectangleEdge.LEFT || a === jsfc.RectangleEdge.RIGHT) {
      b = jsfc.LabelOrientation.PERPENDICULAR;
    } else {
      if (a === jsfc.RectangleEdge.TOP || a === jsfc.RectangleEdge.BOTTOM) {
        b = jsfc.LabelOrientation.PARALLEL;
      } else {
        throw Error("Unrecognised 'edge' code: " + a);
      }
    }
  }
  return b;
};
jsfc.StandardCategoryAxis.prototype.ticks = function(a, b, c) {
  return this._categories.map(function(a) {
    return new jsfc.TickMark(0, a + "");
  });
};
jsfc.StandardCategoryAxis.prototype.reserveSpace = function(a, b, c, d, e) {
  b = 0;
  if (this._label) {
    a.setFont(this._labelFont);
    c = a.textDim(this._label);
    var f = this._labelMargin;
    b += c.height();
    if (jsfc.RectangleEdge.isTopOrBottom(e)) {
      b += f.top() + f.bottom();
    } else {
      if (jsfc.RectangleEdge.isLeftOrRight(e)) {
        b += f.left() + f.right();
      } else {
        throw Error("Unrecognised edge code: " + e);
      }
    }
  }
  d = this.ticks(a, d, e);
  a.setFont(this._tickLabelFont);
  c = this._resolveTickLabelOrientation(e);
  if (c === jsfc.LabelOrientation.PERPENDICULAR) {
    var g = 0;
    d.forEach(function(b) {
      g = Math.max(g, a.textDim(b.label).width());
    });
    b += g;
  } else {
    c === jsfc.LabelOrientation.PARALLEL && (c = a.textDim("123"), b += c.height());
  }
  if (jsfc.RectangleEdge.isTopOrBottom(e)) {
    b += this._tickLabelMargin.top() + this._tickLabelMargin.bottom();
  } else {
    if (jsfc.RectangleEdge.isLeftOrRight(e)) {
      b += this._tickLabelMargin.left() + this._tickLabelMargin.right();
    } else {
      throw Error("Unrecognised edge code: " + e);
    }
  }
  return b;
};
jsfc.StandardCategoryAxis.prototype.draw = function(a, b, c, d, e) {
  c = b.axisPosition(this);
  var f = c === jsfc.RectangleEdge.LEFT, g = c === jsfc.RectangleEdge.RIGHT;
  b = c === jsfc.RectangleEdge.TOP;
  var h = c === jsfc.RectangleEdge.BOTTOM;
  c = this.ticks(a, d, c);
  var k = d.x(), l = d.y(), m = d.width();
  d = d.height();
  var n = e + this._tickMarkOuterLength;
  if (!f && !g && (b || h)) {
    a.setFont(this._tickLabelFont);
    a.setFillColor(this._tickLabelColor);
    n = b ? n + this._tickLabelMargin.bottom() : n + this._tickLabelMargin.top();
    for (f = 0;f < c.length;f++) {
      g = c[f], h = this.keyToRange(g.label, k, k + m).value(0.5), 0 < this._tickMarkInnerLength + this._tickMarkOuterLength && (a.setLineStroke(this._tickMarkStroke), a.setLineColor(this._tickMarkColor), b ? (a.drawLine(h, l - e - this._tickMarkOuterLength, h, l - e + this._tickMarkInnerLength), a.drawAlignedString(g.label, h, l - n, jsfc.TextAnchor.BOTTOM_CENTER)) : (a.drawLine(h, l + d + e - this._tickMarkInnerLength, h, l + d + e + this._tickMarkOuterLength), a.drawAlignedString(g.label, h, l + 
      d + n, jsfc.TextAnchor.TOP_CENTER)));
    }
    a.setLineColor(this._axisLineColor);
    a.setLineStroke(this._axisLineStroke);
    b ? a.drawLine(k, l - e, k + m, l - e) : a.drawLine(k, l + d + e, k + m, l + d + e);
    this._label && (a.setFont(this._labelFont), a.setFillColor(this._labelColor), b ? a.drawAlignedString(this._label, k + m / 2, l - n - this._tickLabelMargin.bottom() - this._labelMargin.top() - this._tickLabelFont.size, jsfc.TextAnchor.BOTTOM_CENTER) : a.drawAlignedString(this._label, k + m / 2, l + d + n + this._tickLabelMargin.bottom() + this._labelMargin.top() + this._tickLabelFont.size, jsfc.TextAnchor.TOP_CENTER));
  }
  this._label && (a.setFont(this._labelFont), a.setFillColor(this._labelColor), b ? a.drawAlignedString(this._label, k + m / 2, l - n - this._tickLabelMargin.bottom() - this._labelMargin.top() - this._tickLabelFont.size, jsfc.TextAnchor.BOTTOM_CENTER) : a.drawAlignedString(this._label, k + m / 2, l + d + n + this._tickLabelMargin.bottom() + this._labelMargin.top() + this._tickLabelFont.size, jsfc.TextAnchor.TOP_CENTER));
};
jsfc.StandardCategoryAxis.prototype.coordinateToKey = function(a, b, c) {
  return null;
};
jsfc.StandardCategoryAxis.prototype.keyToRange = function(a, b, c) {
  var d = jsfc.Utils.findItemInArray(a, this._categories);
  if (0 > d) {
    throw Error("Key is not present in the axis. " + a);
  }
  a = c - b;
  b = Math.round(b + this._lowerMargin * a);
  c = Math.round(c - this._upperMargin * a) - b;
  a = this._categories.length;
  var e = 1 < a ? c * this._categoryMargin : 0, d = b + c / a * d + d / a * (1 < a ? e / (a - 1) : 0);
  return new jsfc.Range(d, d + (c - e) / a);
};
jsfc.StandardCategoryAxis.prototype.itemRange = function(a, b, c, d, e) {
  c = this.keyToRange(c, d, e);
  b = c.length() / b;
  a = c.lowerBound() + a * b;
  return new jsfc.Range(a, a + b);
};
jsfc.StandardCategoryAxis.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.StandardCategoryAxis.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.StandardCategoryAxis.prototype.notifyListeners = function() {
  var a = this;
  this._listeners.forEach(function(b) {
    b(a);
  });
};
jsfc.AxisSpace = function(a, b, c, d) {
  if (!(this instanceof jsfc.AxisSpace)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.Args.requireNumber(a, "top");
  jsfc.Args.requireNumber(b, "left");
  jsfc.Args.requireNumber(c, "bottom");
  jsfc.Args.requireNumber(d, "right");
  this._top = a;
  this._left = b;
  this._bottom = c;
  this._right = d;
};
jsfc.AxisSpace.prototype.top = function() {
  return this._top;
};
jsfc.AxisSpace.prototype.left = function() {
  return this._left;
};
jsfc.AxisSpace.prototype.bottom = function() {
  return this._bottom;
};
jsfc.AxisSpace.prototype.right = function() {
  return this._right;
};
jsfc.AxisSpace.prototype.extend = function(a, b) {
  jsfc.Args.requireNumber(a, "space");
  if (b === jsfc.RectangleEdge.TOP) {
    this._top += a;
  } else {
    if (b === jsfc.RectangleEdge.BOTTOM) {
      this._bottom += a;
    } else {
      if (b === jsfc.RectangleEdge.LEFT) {
        this._left += a;
      } else {
        if (b === jsfc.RectangleEdge.RIGHT) {
          this._right += a;
        } else {
          throw Error("Unrecognised 'edge' code: " + b);
        }
      }
    }
  }
  return this;
};
jsfc.AxisSpace.prototype.innerRect = function(a) {
  var b = a.x() + this._left, c = a.y() + this._top, d = a.width() - this._left - this._right;
  a = a.height() - this._top - this._bottom;
  return new jsfc.Rectangle(b, c, d, a);
};
jsfc.BaseValueAxis = function(a, b) {
  if (!(this instanceof jsfc.BaseValueAxis)) {
    throw Error("Use 'new' for construction.");
  }
  b || (b = this);
  a || (a = null);
  jsfc.BaseValueAxis.init(a, b);
};
jsfc.BaseValueAxis.init = function(a, b) {
  b._label = a;
  b._listeners = [];
  b._labelFont = new jsfc.Font("Palatino;serif", 12, !0, !1);
  b._labelColor = new jsfc.Color(0, 0, 0);
  b._labelMargin = new jsfc.Insets(2, 2, 2, 2);
  b._tickLabelFont = new jsfc.Font("Palatino;serif", 12);
  b._tickLabelColor = new jsfc.Color(0, 0, 0);
  b._axisLineColor = new jsfc.Color(100, 100, 100);
  b._axisLineStroke = new jsfc.Stroke(0.5);
  b._gridLinesVisible = !0;
  b._gridLineStroke = new jsfc.Stroke(1);
  b._gridLineColor = new jsfc.Color(255, 255, 255);
};
jsfc.BaseValueAxis.prototype.getLabel = function() {
  return this._label;
};
jsfc.BaseValueAxis.prototype.setLabel = function(a, b) {
  this._label = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getLabelFont = function() {
  return this._labelFont;
};
jsfc.BaseValueAxis.prototype.setLabelFont = function(a, b) {
  this._labelFont = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getLabelColor = function() {
  return this._labelColor;
};
jsfc.BaseValueAxis.prototype.setLabelColor = function(a, b) {
  this._labelColor = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getLabelMargin = function() {
  return this._labelMargin;
};
jsfc.BaseValueAxis.prototype.setLabelMargin = function(a, b) {
  this._labelMargin = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getTickLabelFont = function() {
  return this._tickLabelFont;
};
jsfc.BaseValueAxis.prototype.setTickLabelFont = function(a, b) {
  this._tickLabelFont = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getTickLabelColor = function() {
  return this._tickLabelColor;
};
jsfc.BaseValueAxis.prototype.setTickLabelColor = function(a, b) {
  this._tickLabelColor = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getAxisLineColor = function() {
  return this._axisLineColor;
};
jsfc.BaseValueAxis.prototype.setAxisLineColor = function(a, b) {
  this._axisLineColor = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getAxisLineStroke = function() {
  return this._axisLineStroke;
};
jsfc.BaseValueAxis.prototype.setAxisLineStroke = function(a, b) {
  this._axisLineStroke = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.BaseValueAxis.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.BaseValueAxis.prototype.notifyListeners = function() {
  var a = this;
  this._listeners.forEach(function(b) {
    b(a);
  });
};
jsfc.BaseValueAxis.prototype.isGridLinesVisible = function() {
  return this._gridLinesVisible;
};
jsfc.BaseValueAxis.prototype.setGridLinesVisible = function(a, b) {
  this._gridLinesVisible = !1 !== a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getGridLineStroke = function() {
  return this._gridLineStroke;
};
jsfc.BaseValueAxis.prototype.setGridLineStroke = function(a, b) {
  this._gridLineStroke = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseValueAxis.prototype.getGridLineColor = function() {
  return this._gridLineColor;
};
jsfc.BaseValueAxis.prototype.setGridLineColor = function(a, b) {
  this._gridLineColor = a;
  !1 !== b && this.notifyListeners();
};
jsfc.Crosshair = function(a) {
  if (!(this instanceof jsfc.Crosshair)) {
    throw Error("Use 'new' with constructor.");
  }
  this._value = a;
  this._color = jsfc.Colors.BLUE;
  this._stroke = new jsfc.Stroke(0.5);
  this._label = null;
  this._labelAnchor = jsfc.RefPt2D.TOP_RIGHT;
  this._labelFont = new jsfc.Font("sans-serif", 10);
  this._labelColor = jsfc.Colors.BLACK;
  this._labelMargin = new jsfc.Insets(4, 4, 4, 4);
  this._labelPadding = new jsfc.Insets(2, 2, 2, 2);
  this._labelBackground = new jsfc.StandardRectanglePainter(new jsfc.Color(255, 255, 100, 100));
};
jsfc.Crosshair.prototype.getValue = function() {
  return this._value;
};
jsfc.Crosshair.prototype.setValue = function(a) {
  this._value = a;
};
jsfc.Crosshair.prototype.getColor = function() {
  return this._color;
};
jsfc.Crosshair.prototype.setColor = function(a) {
  this._color = a;
};
jsfc.Crosshair.prototype.getStroke = function() {
  return this._stroke;
};
jsfc.Crosshair.prototype.setStroke = function(a) {
  this._stroke = a;
};
jsfc.Crosshair.prototype.getLabel = function() {
  return this._label;
};
jsfc.Crosshair.prototype.setLabel = function(a) {
  this._label = a;
};
jsfc.Crosshair.prototype.getLabelAnchor = function() {
  return this._labelAnchor;
};
jsfc.Crosshair.prototype.setLabelAnchor = function(a) {
  this._labelAnchor = a;
};
jsfc.Crosshair.prototype.getLabelFont = function() {
  return this._labelFont;
};
jsfc.Crosshair.prototype.setLabelColor = function(a) {
  this._labelColor = a;
};
jsfc.Crosshair.prototype.getLabelMargin = function() {
  return this._labelMargin;
};
jsfc.Crosshair.prototype.setLabelMargin = function(a) {
  this._labelMargin = a;
};
jsfc.Crosshair.prototype.getLabelPadding = function() {
  return this._labelPadding;
};
jsfc.Crosshair.prototype.setLabelPadding = function(a) {
  this._labelPadding = a;
};
jsfc.Crosshair.prototype.getLabelBackground = function() {
  return this._labelBackground;
};
jsfc.Crosshair.prototype.setLabelBackground = function(a) {
  this._labelBackground = a;
};
jsfc.Crosshair.prototype.drawHorizontal = function(a, b, c) {
  a.setLineStroke(this._stroke);
  a.setLineColor(this._color);
  a.setHint("pointer-events", "none");
  a.drawLine(c.minX(), b, c.maxX(), b);
  a.setHint("pointer-events", null);
  if (this._label) {
    a.setFont(this._labelFont);
    var d = a.textDim(this._label), e = this._labelPadding, f = this._labelMargin, g = this._labelAnchor, h = d.width() + e.left() + e.right(), d = d.height() + e.top() + e.bottom(), k = c.centerX() - h / 2;
    jsfc.RefPt2D.isLeft(g) ? k = c.minX() + f.left() : jsfc.RefPt2D.isRight(g) && (k = c.maxX() - f.right() - h);
    b -= d / 2;
    jsfc.RefPt2D.isTop(g) ? b -= f.bottom() + d / 2 : jsfc.RefPt2D.isBottom(g) && (b += f.top() + d / 2);
    var l = new jsfc.Rectangle(k, b, h, d);
    c.containsRect(l) || (c = d + f.bottom() + f.top(), jsfc.RefPt2D.isTop(g) ? l = new jsfc.Rectangle(k, b + c, h, d) : jsfc.RefPt2D.isBottom(g) && (l = new jsfc.Rectangle(k, b - c, h, d)));
    this._labelBackground && this._labelBackground.paint(a, l);
    a.setFillColor(this._labelColor);
    a.drawAlignedString(this._label, l.x() + e.left(), l.y() + e.top(), jsfc.TextAnchor.TOP_LEFT);
  }
};
jsfc.Crosshair.prototype.drawVertical = function(a, b, c) {
  a.setLineStroke(this._stroke);
  a.setLineColor(this._color);
  a.setHint("pointer-events", "none");
  a.drawLine(b, c.minY(), b, c.maxY());
  a.setHint("pointer-events", null);
  if (this._label) {
    a.setFont(this._labelFont);
    var d = a.textDim(this._label), e = this._labelPadding, f = this._labelMargin, g = this._labelAnchor, h = d.width() + e.left() + e.right(), d = d.height() + e.top() + e.bottom();
    b -= h / 2;
    jsfc.RefPt2D.isLeft(g) ? b -= h / 2 + f.right() : jsfc.RefPt2D.isRight(g) && (b += h / 2 + f.left());
    var k = c.centerY();
    jsfc.RefPt2D.isTop(g) ? k = c.minY() + f.top() : jsfc.RefPt2D.isBottom(g) && (k = c.maxY() - f.bottom() - d);
    var l = new jsfc.Rectangle(b, k, h, d);
    c.containsRect(l) || (c = h + f.left() + f.right(), jsfc.RefPt2D.isLeft(g) ? l = new jsfc.Rectangle(b + c, k, h, d) : jsfc.RefPt2D.isRight(g) && (l = new jsfc.Rectangle(b - c, k, h, d)));
    this._labelBackground && this._labelBackground.paint(a, l);
    a.setFillColor(this._labelColor);
    a.drawAlignedString(this._label, l.x() + e.left(), l.y() + e.top(), jsfc.TextAnchor.TOP_LEFT);
  }
};
jsfc.LabelOrientation = {PERPENDICULAR:"PERPENDICULAR", PARALLEL:"PARALLEL"};
Object.freeze && Object.freeze(jsfc.LabelOrientation);
jsfc.LinearAxis = function(a, b) {
  if (!(this instanceof jsfc.LinearAxis)) {
    throw Error("Use 'new' with constructor.");
  }
  b || (b = this);
  jsfc.LinearAxis.init(a, b);
};
jsfc.LinearAxis.init = function(a, b) {
  jsfc.BaseValueAxis.init(a, b);
  b._lowerBound = 0;
  b._upperBound = 1;
  b._autoRange = !0;
  b._autoRangeIncludesZero = !1;
  b._lowerMargin = 0.05;
  b._upperMargin = 0.05;
  b._defaultRange = new jsfc.Range(0, 1);
  b._tickSelector = new jsfc.NumberTickSelector(!1);
  b._formatter = new jsfc.NumberFormat(2);
  b._tickMarkInnerLength = 0;
  b._tickMarkOuterLength = 2;
  b._tickMarkStroke = new jsfc.Stroke(0.5);
  b._tickMarkColor = new jsfc.Color(100, 100, 100);
  b._tickLabelMargin = new jsfc.Insets(2, 2, 2, 2);
  b._tickLabelFactor = 1.4;
  b._tickLabelOrientation = null;
  b._tickLabelFormatOverride = null;
  b._symbols = [];
};
jsfc.LinearAxis.prototype = new jsfc.BaseValueAxis;
jsfc.LinearAxis.prototype.getLowerBound = function() {
  return this._lowerBound;
};
jsfc.LinearAxis.prototype.getUpperBound = function() {
  return this._upperBound;
};
jsfc.LinearAxis.prototype.length = function() {
  return this._upperBound - this._lowerBound;
};
jsfc.LinearAxis.prototype.contains = function(a) {
  return a >= this._lowerBound && a <= this._upperBound;
};
jsfc.LinearAxis.prototype.setBounds = function(a, b, c, d) {
  if (a >= b) {
    throw Error("Require upper > lower: " + a + " > " + b);
  }
  this._lowerBound = a;
  this._upperBound = b;
  d || (this._autoRange = !1);
  !1 !== c && this.notifyListeners();
  return this;
};
jsfc.LinearAxis.prototype.setBoundsByPercent = function(a, b, c, d) {
  var e = this._lowerBound, f = this._upperBound - e;
  a = e + a * f;
  b = e + b * f;
  b > a && isFinite(b - a) && (this._lowerBound = a, this._upperBound = b, d || (this._autoRange = !1), !1 !== c && this.notifyListeners());
  return this;
};
jsfc.LinearAxis.prototype.isAutoRange = function() {
  return this._autoRange;
};
jsfc.LinearAxis.prototype.setAutoRange = function(a, b) {
  this._autoRange = a;
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.LinearAxis.prototype.getAutoRangeIncludesZero = function() {
  return this._autoRangeIncludesZero;
};
jsfc.LinearAxis.prototype.setAutoRangeIncludesZero = function(a, b) {
  this._autoRangeIncludesZero = a;
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.LinearAxis.prototype.getLowerMargin = function() {
  return this._lowerMargin;
};
jsfc.LinearAxis.prototype.setLowerMargin = function(a, b) {
  this._lowerMargin = a;
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.LinearAxis.prototype.getUpperMargin = function() {
  return this._upperMargin;
};
jsfc.LinearAxis.prototype.setUpperMargin = function(a, b) {
  this._upperMargin = a;
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.LinearAxis.prototype.getTickLabelFormatOverride = function() {
  return this._tickLabelFormatOverride;
};
jsfc.LinearAxis.prototype.setTickLabelFormatOverride = function(a, b) {
  this._tickLabelFormatOverride = a;
  !1 !== b && this.notifyListeners();
  return this;
};
jsfc.LinearAxis.prototype._applyAutoRange = function(a, b) {
  this._autoRangeIncludesZero && (0 < a && (a = 0), 0 > b && (b = 0));
  var c = b - a, d;
  0 < c ? (d = this._lowerMargin * c, c *= this._upperMargin) : (d = 0.5 * this._defaultRange.length(), c = 0.5 * this._defaultRange.length());
  this.setBounds(a - d, b + c, !1, !0);
};
jsfc.LinearAxis.prototype.valueToCoordinate = function(a, b, c) {
  jsfc.Args.requireNumber(b, "r0");
  jsfc.Args.requireNumber(c, "r1");
  var d = this._lowerBound;
  return b + (a - d) / (this._upperBound - d) * (c - b);
};
jsfc.LinearAxis.prototype.coordinateToValue = function(a, b, c) {
  var d = this._lowerBound;
  return d + (a - b) / (c - b) * (this._upperBound - d);
};
jsfc.LinearAxis.prototype._calcTickSize = function(a, b, c) {
  var d = Number.NaN, e = b.length(c);
  b = this._upperBound - this._lowerBound;
  if (0 >= b) {
    throw Error("Can't have a zero range.");
  }
  var f = this._resolveTickLabelOrientation(c);
  c = this._tickSelector;
  if (f === jsfc.LabelOrientation.PERPENDICULAR) {
    if (a = a.textDim("123").height(), a = e / (a * this._tickLabelFactor), 2 < a) {
      e = c.select(b / 2);
      e = Math.floor(b / e);
      for (d = !0;e < a && d;) {
        d = c.previous(), e = Math.floor(b / c.currentTickSize());
      }
      d && c.next();
      d = c.currentTickSize();
      this._formatter = c.currentTickFormat();
    }
  } else {
    if (f === jsfc.LabelOrientation.PARALLEL) {
      c.select(b);
      a.setFont(this._tickLabelFont);
      for (d = !1;!d;) {
        if (c.previous()) {
          var g = c.currentTickFormat();
          this._formatter = g;
          var f = g.format(this._lowerBound), g = g.format(this._upperBound), h = a.textDim(f).width(), k = a.textDim(g).width(), h = Math.max(h, k);
          if (0 == h && 0 < f.length && 0 < g.length) {
            return Number.NaN;
          }
          Math.floor(e / (h * this._tickLabelFactor)) < b / c.currentTickSize() && (c.next(), this._formatter = c.currentTickFormat(), d = !0);
        } else {
          d = !0;
        }
      }
      d = c.currentTickSize();
    }
  }
  return d;
};
jsfc.LinearAxis.prototype._resolveTickLabelOrientation = function(a) {
  var b = this._tickLabelOrientation;
  if (!b) {
    if (a === jsfc.RectangleEdge.LEFT || a === jsfc.RectangleEdge.RIGHT) {
      b = jsfc.LabelOrientation.PERPENDICULAR;
    } else {
      if (a === jsfc.RectangleEdge.TOP || a === jsfc.RectangleEdge.BOTTOM) {
        b = jsfc.LabelOrientation.PARALLEL;
      } else {
        throw Error("Unrecognised 'edge' code: " + a);
      }
    }
  }
  return b;
};
jsfc.LinearAxis.prototype.reserveSpace = function(a, b, c, d, e) {
  b = this._tickMarkOuterLength;
  if (this._label) {
    a.setFont(this._labelFont);
    c = a.textDim(this._label);
    var f = this._labelMargin;
    b += c.height();
    if (jsfc.RectangleEdge.isTopOrBottom(e)) {
      b += f.top() + f.bottom();
    } else {
      if (jsfc.RectangleEdge.isLeftOrRight(e)) {
        b += f.left() + f.right();
      } else {
        throw Error("Unrecognised edge code: " + e);
      }
    }
  }
  c = this._calcTickSize(a, d, e);
  d = this.ticks(c, a, d, e);
  a.setFont(this._tickLabelFont);
  c = this._resolveTickLabelOrientation(e);
  if (c === jsfc.LabelOrientation.PERPENDICULAR) {
    var g = 0;
    d.forEach(function(b) {
      g = Math.max(g, a.textDim(b.label).width());
    });
    b += g;
  } else {
    c === jsfc.LabelOrientation.PARALLEL && (c = a.textDim("123"), b += c.height());
  }
  if (jsfc.RectangleEdge.isTopOrBottom(e)) {
    b += this._tickLabelMargin.top() + this._tickLabelMargin.bottom();
  } else {
    if (jsfc.RectangleEdge.isLeftOrRight(e)) {
      b += this._tickLabelMargin.left() + this._tickLabelMargin.right();
    } else {
      throw Error("Unrecognised edge code: " + e);
    }
  }
  return b;
};
jsfc.LinearAxis.prototype._symbolCount = function(a) {
  var b = 0;
  this._symbols.forEach(function(c) {
    a.contains(c.value) && b++;
  });
  return b;
};
jsfc.LinearAxis.prototype.ticks = function(a, b, c, d) {
  var e = this._lowerBound, f = this._upperBound;
  b = new jsfc.Range(e, f);
  if (0 < this._symbolCount(b)) {
    var g = [];
    this._symbols.forEach(function(a) {
      a.value > e && a.value < f && g.push(new jsfc.TickMark(a.value, a.symbol));
    });
    return g;
  }
  b = this._tickLabelFormatOverride || this._formatter;
  g = [];
  if (!isNaN(a)) {
    d = c = Math.ceil(e / a) * a;
    for (var h = 0;c < f;) {
      var k = c, l = new jsfc.TickMark(c, b.format(c));
      for (g.push(l);c === k;) {
        c = d + h * a, h++;
      }
    }
  }
  2 > g.length && (a = new jsfc.TickMark(e, b.format(e)), b = new jsfc.TickMark(f, b.format(f)), g = [a, b]);
  return g;
};
jsfc.LinearAxis.prototype.draw = function(a, b, c, d, e) {
  var f = b.axisPosition(this);
  b = this._calcTickSize(a, d, f);
  b = this.ticks(b, a, d, f);
  c = d.x();
  var g = d.y(), h = d.width(), k = d.height(), l = f === jsfc.RectangleEdge.RIGHT, m = f === jsfc.RectangleEdge.TOP, n = f === jsfc.RectangleEdge.BOTTOM;
  if (f === jsfc.RectangleEdge.LEFT || l) {
    a.setFont(this._tickLabelFont);
    a.setFillColor(this._tickLabelColor);
    for (f = m = 0;f < b.length;f++) {
      var n = b[f], p = this.valueToCoordinate(n.value, g + k, g);
      this._gridLinesVisible && (a.setLineStroke(this._gridLineStroke), a.setLineColor(this._gridLineColor), a.drawLine(c, Math.round(p), c + h, Math.round(p)));
      0 < this._tickMarkInnerLength + this._tickMarkOuterLength && (a.setLineStroke(this._tickMarkStroke), a.setLineColor(this._tickMarkColor), l ? a.drawLine(c + h + e - this._tickMarkInnerLength, p, c + h + e + this._tickMarkOuterLength, p) : a.drawLine(c - e - this._tickMarkOuterLength, p, c - e + this._tickMarkInnerLength, p));
      if (l) {
        var r = e + this._tickMarkOuterLength + this._tickLabelMargin.left(), n = a.drawAlignedString(n.label, c + h + r, p, jsfc.TextAnchor.CENTER_LEFT)
      } else {
        r = e + this._tickMarkOuterLength + this._tickLabelMargin.right(), n = a.drawAlignedString(n.label, c - r, p, jsfc.TextAnchor.CENTER_RIGHT);
      }
      m = Math.max(m, n.width());
    }
    a.setLineColor(this._axisLineColor);
    a.setLineStroke(this._axisLineStroke);
    l ? a.drawLine(c + h + e, g, c + h + e, g + d.height()) : a.drawLine(c - e, g, c - e, g + d.height());
    this._label && (a.setFont(this._labelFont), a.setFillColor(this._labelColor), l ? (r = e + m + this._tickMarkOuterLength + this._tickLabelMargin.left() + this._tickLabelMargin.right() + this._labelMargin.left(), a.drawRotatedString(this._label, c + h + r, g + k / 2, jsfc.TextAnchor.BOTTOM_CENTER, Math.PI / 2)) : (r = e + m + this._tickMarkOuterLength + this._tickLabelMargin.left() + this._tickLabelMargin.right() + this._labelMargin.right(), a.drawRotatedString(this._label, c - r, g + k / 2, jsfc.TextAnchor.BOTTOM_CENTER, 
    -Math.PI / 2)));
  } else {
    if (m || n) {
      a.setFont(this._tickLabelFont);
      a.setFillColor(this._tickLabelColor);
      d = e + this._tickMarkOuterLength;
      d = m ? d + this._tickLabelMargin.bottom() : d + this._tickLabelMargin.top();
      for (f = 0;f < b.length;f++) {
        n = b[f], l = this.valueToCoordinate(n.value, c, c + h), this._gridLinesVisible && (a.setLineStroke(this._gridLineStroke), a.setLineColor(this._gridLineColor), a.drawLine(Math.round(l), g, Math.round(l), g + k)), 0 < this._tickMarkInnerLength + this._tickMarkOuterLength && (a.setLineStroke(this._tickMarkStroke), a.setLineColor(this._tickMarkColor), m ? (a.drawLine(l, g - e - this._tickMarkOuterLength, l, g - e + this._tickMarkInnerLength), a.drawAlignedString(n.label, l, g - d, jsfc.TextAnchor.BOTTOM_CENTER)) : 
        (a.drawLine(l, g + k + e - this._tickMarkInnerLength, l, g + k + e + this._tickMarkOuterLength), a.drawAlignedString(n.label, l, g + k + d, jsfc.TextAnchor.TOP_CENTER)));
      }
      a.setLineColor(this._axisLineColor);
      a.setLineStroke(this._axisLineStroke);
      m ? a.drawLine(c, g - e, c + h, g - e) : a.drawLine(c, g + k + e, c + h, g + k + e);
      this._label && (a.setFont(this._labelFont), a.setFillColor(this._labelColor), m ? a.drawAlignedString(this._label, c + h / 2, g - d - this._tickLabelMargin.bottom() - this._labelMargin.top() - this._tickLabelFont.size, jsfc.TextAnchor.BOTTOM_CENTER) : a.drawAlignedString(this._label, c + h / 2, g + k + d + this._tickLabelMargin.bottom() + this._labelMargin.top() + this._tickLabelFont.size, jsfc.TextAnchor.TOP_CENTER));
    }
  }
};
jsfc.LinearAxis.prototype.configureAsXAxis = function(a) {
  var b = a.getDataset();
  if (this._autoRange && b) {
    var c = a.getDataset().xbounds();
    c[0] <= c[1] && this._applyAutoRange(c[0], c[1]);
  }
  b && (this._symbols = (a = a.getDataset().getProperty("x-symbols")) ? a.map(function(a) {
    return a;
  }) : []);
};
jsfc.LinearAxis.prototype.configureAsYAxis = function(a) {
  var b = a.getDataset();
  if (this._autoRange && b) {
    var c = a.getRenderer().calcYRange(b);
    0 <= c.length() && this._applyAutoRange(c.lowerBound(), c.upperBound());
  }
  b && (this._symbols = (a = a.getDataset().getProperty("y-symbols")) ? a.map(function(a) {
    return a;
  }) : []);
};
jsfc.LinearAxis.prototype.resizeRange = function(a, b, c) {
  jsfc.Args.requireNumber(a, "factor");
  if (0 < a) {
    var d = b - (b - this._lowerBound) * a;
    a = b + (this._upperBound - b) * a;
    a > d && isFinite(a - d) && (this._lowerBound = d, this._upperBound = a, this._autoRange = !1, !1 !== c && this.notifyListeners());
  } else {
    this.setAutoRange(!0);
  }
};
jsfc.LinearAxis.prototype.pan = function(a, b) {
  jsfc.Args.requireNumber(a, "percent");
  var c = a * (this._upperBound - this._lowerBound), d = this._lowerBound + c, c = this._upperBound + c;
  isFinite(d) && isFinite(c) && (this._lowerBound = d, this._upperBound = c, this._autoRange = !1, !1 !== b && this.notifyListeners());
};
jsfc.NumberTickSelector = function(a) {
  if (!(this instanceof jsfc.NumberTickSelector)) {
    throw Error("Use 'new' for constructor.");
  }
  this._power = 0;
  this._factor = 1;
  this._percentage = a;
  this._f0 = new jsfc.NumberFormat(0);
  this._f1 = new jsfc.NumberFormat(1);
  this._f2 = new jsfc.NumberFormat(2);
  this._f3 = new jsfc.NumberFormat(3);
  this._f4 = new jsfc.NumberFormat(4);
};
jsfc.NumberTickSelector.prototype.select = function(a) {
  this._power = Math.ceil(Math.LOG10E * Math.log(a));
  this._factor = 1;
  return this.currentTickSize();
};
jsfc.NumberTickSelector.prototype.currentTickSize = function() {
  return this._factor * Math.pow(10, this._power);
};
jsfc.NumberTickSelector.prototype.currentTickFormat = function() {
  return-4 === this._power ? this._f4 : -3 === this._power ? this._f3 : -2 === this._power ? this._f2 : -1 === this._power ? this._f1 : -4 > this._power ? new jsfc.NumberFormat(Number.POSITIVE_INFINITY) : 6 < this._power ? new jsfc.NumberFormat(1, !0) : this._f0;
};
jsfc.NumberTickSelector.prototype.next = function() {
  if (300 === this._power && 5 === this._factor) {
    return!1;
  }
  if (1 === this._factor) {
    return this._factor = 2, !0;
  }
  if (2 === this._factor) {
    return this._factor = 5, !0;
  }
  if (5 === this._factor) {
    return this._power++, this._factor = 1, !0;
  }
  throw Error("Factor should be 1, 2 or 5: " + this._factor);
};
jsfc.NumberTickSelector.prototype.previous = function() {
  if (-300 === this._power && 1 === this._factor) {
    return!1;
  }
  if (1 === this._factor) {
    return this._factor = 5, this._power--, !0;
  }
  if (2 === this._factor) {
    return this._factor = 1, !0;
  }
  if (5 === this._factor) {
    return this._factor = 2, !0;
  }
  throw Error("Factor should be 1, 2 or 5: " + this._factor);
};
jsfc.TickMark = function(a, b) {
  if (!(this instanceof jsfc.TickMark)) {
    throw Error("Use 'new' for constructor.");
  }
  this.value = a;
  this.label = b;
};
jsfc.TickMark.prototype.toString = function() {
  return this.label;
};
jsfc.ColorSource = function(a) {
  if (!(this instanceof jsfc.ColorSource)) {
    throw Error("Use 'new' for constructor.");
  }
  this._colors = a;
};
jsfc.ColorSource.prototype.getColor = function(a, b) {
  return this._colors[a % this._colors.length];
};
jsfc.ColorSource.prototype.getLegendColor = function(a) {
  return this._colors[a % this._colors.length];
};
jsfc.CategoryRenderer = function() {
  throw Error("Documents the interface only.");
};
jsfc.BaseCategoryRenderer = function(a) {
  if (!(this instanceof jsfc.BaseCategoryRenderer)) {
    throw Error("Use 'new' for constructor.");
  }
  a || (a = this);
  jsfc.BaseCategoryRenderer.init(a);
};
jsfc.BaseCategoryRenderer.init = function(a) {
  var b = jsfc.Colors.colorsAsObjects(jsfc.Colors.fancyLight()), c = jsfc.Colors.colorsAsObjects(jsfc.Colors.fancyLight());
  a._lineColorSource = new jsfc.ColorSource(b);
  a._fillColorSource = new jsfc.ColorSource(c);
  a._listeners = [];
};
jsfc.BaseCategoryRenderer.prototype.getLineColorSource = function() {
  return this._lineColorSource;
};
jsfc.BaseCategoryRenderer.prototype.setLineColorSource = function(a, b) {
  this._lineColorSource = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseCategoryRenderer.prototype.getFillColorSource = function() {
  return this._fillColorSource;
};
jsfc.BaseCategoryRenderer.prototype.setFillColorSource = function(a, b) {
  this._fillColorSource = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseCategoryRenderer.prototype.passCount = function() {
  return 1;
};
jsfc.BaseCategoryRenderer.prototype.calcYRange = function(a) {
  a = jsfc.Values2DDatasetUtils.ybounds(a);
  return new jsfc.Range(a[0], a[1]);
};
jsfc.BaseCategoryRenderer.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.BaseCategoryRenderer.prototype.removeListener = function(a) {
  a = this._listeners.indexOf(a);
  0 <= a && this._listeners.splice(a, 1);
  return this;
};
jsfc.BaseCategoryRenderer.prototype.notifyListeners = function() {
  var a = this;
  this._listeners.forEach(function(b) {
    b(a);
  });
};
jsfc.BarRenderer = function(a) {
  if (!(this instanceof jsfc.BarRenderer)) {
    throw Error("Use 'new' for constructors.");
  }
  jsfc.BaseCategoryRenderer.init(this);
  this._plot = a;
};
jsfc.BarRenderer.prototype = new jsfc.BaseCategoryRenderer;
jsfc.BarRenderer.prototype.itemFillColor = function(a, b, c) {
  b = a.rowIndex(b);
  a = a.columnIndex(c);
  return this._lineColorSource.getColor(b, a);
};
jsfc.BarRenderer.prototype.itemStrokeColor = function(a, b) {
  return "none";
};
jsfc.BarRenderer.prototype.calcYRange = function(a) {
  a = jsfc.Values2DDatasetUtils.ybounds(a, 0);
  return new jsfc.Range(a[0], a[1]);
};
jsfc.BarRenderer.prototype.drawItem = function(a, b, c, d, e, f, g) {
  g = d.rowKey(e);
  var h = d.columnKey(f);
  f = d.valueByIndex(e, f);
  var k = c.getXAxis();
  c = c.getYAxis();
  var l = d.rowCount();
  e = k.itemRange(e, l, h, b.minX(), b.maxX());
  f = c.valueToCoordinate(f, b.maxY(), b.minY());
  b = c.valueToCoordinate(0, b.maxY(), b.minY());
  d = this.itemFillColor(d, g, h);
  a.setFillColor(d);
  a.setLineColor(new jsfc.Color(50, 50, 50));
  a.drawRect(e.lowerBound(), Math.min(f, b), e.length(), Math.abs(b - f));
};
jsfc.StackedBarRenderer = function(a) {
  if (!(this instanceof jsfc.StackedBarRenderer)) {
    throw Error("Use 'new' for constructors.");
  }
  jsfc.BaseCategoryRenderer.init(this);
  this._plot = a;
};
jsfc.StackedBarRenderer.prototype = new jsfc.BaseCategoryRenderer;
jsfc.StackedBarRenderer.prototype.itemFillColor = function(a, b, c) {
  b = a.rowIndex(b);
  a = a.columnIndex(c);
  return this._lineColorSource.getColor(b, a);
};
jsfc.StackedBarRenderer.prototype.calcYRange = function(a) {
  a = jsfc.Values2DDatasetUtils.stackYBounds(a, 0);
  return new jsfc.Range(a[0], a[1]);
};
jsfc.StackedBarRenderer.prototype.drawItem = function(a, b, c, d, e, f, g) {
  g = d.rowKey(e);
  var h = d.columnKey(f), k = d.valueByIndex(e, f);
  e = jsfc.Values2DDatasetUtils.stackBaseY(d, e, f);
  var l = c.getXAxis();
  f = c.getYAxis();
  d.rowCount();
  c = l.keyToRange(h, b.minX(), b.maxX());
  k = f.valueToCoordinate(e + k, b.maxY(), b.minY());
  b = f.valueToCoordinate(e, b.maxY(), b.minY());
  d = this.itemFillColor(d, g, h);
  a.setFillColor(d);
  a.setLineColor(new jsfc.Color(50, 50, 50));
  a.drawRect(c.lowerBound(), Math.min(k, b), c.length(), Math.abs(b - k));
};
jsfc.XYRenderer = function() {
  throw Error("Documents the interface only.");
};
jsfc.XYRenderer.prototype.passCount = function() {
};
jsfc.XYRenderer.prototype.drawItem = function(a, b, c, d, e, f, g) {
};
jsfc.BaseXYRenderer = function(a) {
  if (!(this instanceof jsfc.BaseXYRenderer)) {
    throw Error("Use 'new' for constructor.");
  }
  a || (a = this);
  jsfc.BaseXYRenderer.init(a);
};
jsfc.BaseXYRenderer.init = function(a) {
  var b = jsfc.Colors.colorsAsObjects(jsfc.Colors.fancyLight()), c = jsfc.Colors.colorsAsObjects(jsfc.Colors.fancyLight());
  a._lineColorSource = new jsfc.ColorSource(b);
  a._fillColorSource = new jsfc.ColorSource(c);
  a._listeners = [];
};
jsfc.BaseXYRenderer.prototype.getLineColorSource = function() {
  return this._lineColorSource;
};
jsfc.BaseXYRenderer.prototype.setLineColorSource = function(a, b) {
  this._lineColorSource = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseXYRenderer.prototype.getFillColorSource = function() {
  return this._fillColorSource;
};
jsfc.BaseXYRenderer.prototype.setFillColorSource = function(a, b) {
  this._fillColorSource = a;
  !1 !== b && this.notifyListeners();
};
jsfc.BaseXYRenderer.prototype.passCount = function() {
  return 1;
};
jsfc.BaseXYRenderer.prototype.calcYRange = function(a) {
  a = jsfc.XYDatasetUtils.ybounds(a);
  return new jsfc.Range(a[0], a[1]);
};
jsfc.BaseXYRenderer.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.BaseXYRenderer.prototype.notifyListeners = function() {
  var a = this;
  this._listeners.forEach(function(b) {
    b(a);
  });
};
jsfc.ScatterRenderer = function(a) {
  if (!(this instanceof jsfc.ScatterRenderer)) {
    throw Error("Use 'new' for constructors.");
  }
  jsfc.BaseXYRenderer.init(this);
  this._plot = a;
  this._radius = 3;
};
jsfc.ScatterRenderer.prototype = new jsfc.BaseXYRenderer;
jsfc.ScatterRenderer.prototype.itemFillColorStr = function(a, b) {
  var c = this._plot.getDataset().getProperty(a, b, "color");
  return c ? c : this.itemFillColor(a, b).rgbaStr();
};
jsfc.ScatterRenderer.prototype.itemFillColor = function(a, b) {
  var c = this._plot.getDataset(), d = c.seriesIndex(a), c = c.itemIndex(a, b);
  return this._lineColorSource.getColor(d, c);
};
jsfc.ScatterRenderer.prototype.itemStrokeColor = function(a, b) {
  return this._plot._dataset.isSelected("select", a, b) ? "red" : "none";
};
jsfc.ScatterRenderer.prototype.drawItem = function(a, b, c, d, e, f, g) {
  g = d.seriesKey(e);
  var h = d.getItemKey(e, f), k = d.x(e, f);
  f = d.y(e, f);
  e = c.getXAxis().valueToCoordinate(k, b.minX(), b.maxX());
  b = c.getYAxis().valueToCoordinate(f, b.maxY(), b.minY());
  c = d.getItemProperty(g, h, "color");
  c = "string" === typeof c ? jsfc.Color.fromStr(c) : this.itemFillColor(g, h);
  f = this._radius;
  d.isSelected("selection", g, h) && (f *= 2);
  a.setFillColor(c);
  a.setLineStroke(new jsfc.Stroke(0.2));
  a.setLineColor(jsfc.Colors.BLACK);
  a.setHint("ref", [g, h]);
  a.drawCircle(e, b, f);
};
jsfc.XYBarRenderer = function() {
  if (!(this instanceof jsfc.XYBarRenderer)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseXYRenderer.init(this);
};
jsfc.XYBarRenderer.prototype = new jsfc.BaseXYRenderer;
jsfc.XYBarRenderer.prototype.drawItem = function(a, b, c, d, e, f, g) {
  var h = d.xStart(e, f);
  g = d.xEnd(e, f);
  d = d.y(e, f);
  var k = c.getXAxis();
  c = c.getYAxis();
  var l = b.width(), m = b.height(), h = k.valueToCoordinate(h, b.x(), b.x() + l);
  g = k.valueToCoordinate(g, b.x(), b.x() + l);
  d = c.valueToCoordinate(d, b.y() + m, b.y());
  b = c.valueToCoordinate(0, b.y() + m, b.y());
  a.setLineColor(this._lineColorSource.getColor(e, f));
  a.setLineStroke(new jsfc.Stroke(1));
  a.setFillColor(this._fillColorSource.getColor(e, f));
  a.drawRect(h, Math.min(d, b), g - h, Math.abs(d - b));
};
jsfc.XYLineRenderer = function() {
  if (!(this instanceof jsfc.XYLineRenderer)) {
    return new jsfc.XYLineRenderer;
  }
  jsfc.BaseXYRenderer.init(this);
};
jsfc.XYLineRenderer.prototype = new jsfc.BaseXYRenderer;
jsfc.XYLineRenderer.prototype.passCount = function() {
  return 2;
};
jsfc.XYLineRenderer.prototype.drawItem = function(a, b, c, d, e, f, g) {
  var h = d.x(e, f), k = d.y(e, f), h = c.getXAxis().valueToCoordinate(h, b.x(), b.x() + b.width()), k = c.getYAxis().valueToCoordinate(k, b.y() + b.height(), b.y());
  0 === g && 0 < f && (g = d.x(e, f - 1), d = d.y(e, f - 1), g = c.getXAxis().valueToCoordinate(g, b.x(), b.x() + b.width()), b = c.getYAxis().valueToCoordinate(d, b.y() + b.height(), b.y()), a.setLineColor(this._lineColorSource.getColor(e, f)), a.setLineStroke(new jsfc.Stroke(3)), a.drawLine(g, b, h, k));
};
jsfc.CategoryPlot = function(a) {
  if (!(this instanceof jsfc.CategoryPlot)) {
    throw Error("Use 'new' for construction.");
  }
  this._listeners = [];
  this._plotBackground = null;
  this._dataBackground = new jsfc.StandardRectanglePainter(new jsfc.Color(230, 230, 230), new jsfc.Color(0, 0, 0, 0));
  this._renderer = new jsfc.BarRenderer(this);
  this._rendererListener = function(a) {
    return function(c) {
      a.rendererChanged(c);
    };
  }(this);
  this._axisOffsets = new jsfc.Insets(0, 0, 0, 0);
  this._xAxis = new jsfc.StandardCategoryAxis;
  this._xAxisPosition = jsfc.RectangleEdge.BOTTOM;
  this._xAxis.configureAsXAxis(this);
  this._xAxisListener = function(a) {
    return function(c) {
      c.configureAsXAxis(a);
      a.notifyListeners();
    };
  }(this);
  this._xAxis.addListener(this._xAxisListener);
  this._yAxis = new jsfc.LinearAxis;
  this._yAxis.setAutoRangeIncludesZero(!0);
  this._yAxisPosition = jsfc.RectangleEdge.LEFT;
  this._yAxis.configureAsYAxis(this);
  this._yAxisListener = function(a) {
    return function(c) {
      c.isAutoRange() && c.configureAsYAxis(a);
      a.notifyListeners();
    };
  }(this);
  this._yAxis.addListener(this._yAxisListener);
  this.setDataset(a);
  this.itemLabelGenerator = new jsfc.KeyedValue2DLabels;
};
jsfc.CategoryPlot.prototype.getDataset = function() {
  return this._dataset;
};
jsfc.CategoryPlot.prototype.setDataset = function(a, b) {
  this._datasetListener && this._dataset.removeListener(this._datasetListener);
  this._dataset = a;
  this._datasetListener = function(a) {
    return function(b) {
      a.datasetChanged();
    };
  }(this);
  this._dataset.addListener(this._datasetListener);
  this._xAxis.configureAsXAxis(this);
  this._yAxis.configureAsYAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.getBackground = function() {
  return this._plotBackground;
};
jsfc.CategoryPlot.prototype.setBackground = function(a, b) {
  this._plotBackground = a;
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.setBackgroundColor = function(a, b) {
  var c = new jsfc.StandardRectanglePainter(a, null);
  this.setBackground(c, b);
};
jsfc.CategoryPlot.prototype.getDataBackground = function() {
  return this._dataBackground;
};
jsfc.CategoryPlot.prototype.setDataBackground = function(a, b) {
  this._dataBackground = a;
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.setDataBackgroundColor = function(a, b) {
  var c = new jsfc.StandardRectanglePainter(a, null);
  this.setDataBackground(c, b);
};
jsfc.CategoryPlot.prototype.getRenderer = function() {
  return this._renderer;
};
jsfc.CategoryPlot.prototype.setRenderer = function(a, b) {
  this._renderer.removeListener(this._rendererListener);
  this._renderer = a;
  this._renderer.addListener(this._rendererListener);
  this._xAxis.configureAsXAxis(this);
  this._yAxis.configureAsYAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.datasetChanged = function() {
  this._xAxis.configureAsXAxis(this);
  this._yAxis.isAutoRange() && this._yAxis.configureAsYAxis(this);
  this.notifyListeners();
};
jsfc.CategoryPlot.prototype.rendererChanged = function(a) {
  this._xAxis.configureAsXAxis(this);
  this._yAxis.isAutoRange() && this._yAxis.configureAsYAxis(this);
  this.notifyListeners();
};
jsfc.CategoryPlot.prototype.getAxisOffsets = function() {
  return this._axisOffsets;
};
jsfc.CategoryPlot.prototype.setAxisOffsets = function(a, b) {
  this._axisOffsets = a;
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.getXAxis = function() {
  return this._xAxis;
};
jsfc.CategoryPlot.prototype.setXAxis = function(a, b) {
  this._xAxis.removeListener(this._xAxisListener);
  this._xAxis = a;
  this._xAxis.addListener(this._xAxisListener);
  this._xAxis.configureAsXAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.getXAxisPosition = function() {
  return this._xAxisPosition;
};
jsfc.CategoryPlot.prototype.setXAxisPosition = function(a, b) {
  this.xAxisPosition = a;
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.getYAxis = function() {
  return this._yAxis;
};
jsfc.CategoryPlot.prototype.setYAxis = function(a, b) {
  this._yAxis.removeListener(this._yAxisListener);
  this._yAxis = a;
  this._yAxis.addListener(this._yAxisListener);
  this._yAxis.configureAsYAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.getYAxisPosition = function() {
  return this._yAxisPosition;
};
jsfc.CategoryPlot.prototype.setYAxisPosition = function(a, b) {
  this.yAxisPosition = a;
  !1 !== b && this.notifyListeners();
};
jsfc.CategoryPlot.prototype.isYZoomable = function() {
  return!0;
};
jsfc.CategoryPlot.prototype.zoomXAboutAnchor = function(a, b, c) {
  var d = this._dataArea.minX(), e = this._dataArea.maxX();
  b = this._xAxis.coordinateToValue(b, d, e);
  this._xAxis.resizeRange(a, b, !1 !== c);
};
jsfc.CategoryPlot.prototype.zoomX = function(a, b, c) {
  this._xAxis.setBoundsByPercent(a, b, c);
};
jsfc.CategoryPlot.prototype.zoomYAboutAnchor = function(a, b, c) {
  var d = this._dataArea.minY(), e = this._dataArea.maxY();
  b = this._yAxis.coordinateToValue(b, e, d);
  this._yAxis.resizeRange(a, b, !1 !== c);
};
jsfc.CategoryPlot.prototype.zoomY = function(a, b, c) {
  this._yAxis.setBoundsByPercent(a, b, c);
};
jsfc.CategoryPlot.prototype.panX = function(a, b) {
  this._xAxis.pan(a, !1 !== b);
};
jsfc.CategoryPlot.prototype.panY = function(a, b) {
  this._yAxis.pan(a, !1 !== b);
};
jsfc.CategoryPlot.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.CategoryPlot.prototype.notifyListeners = function() {
  var a = this;
  this._listeners.forEach(function(b) {
    b(a);
  });
};
jsfc.CategoryPlot.prototype.draw = function(a, b, c) {
  this._plotBackground && this._plotBackground.paint(a, c);
  var d = new jsfc.AxisSpace(0, 0, 0, 0), e = this.axisPosition(this._xAxis), f = this._xAxis.reserveSpace(a, this, b, c, e);
  d.extend(f, e);
  f = d.innerRect(c);
  e = this.axisPosition(this._yAxis);
  f = this._yAxis.reserveSpace(a, this, b, f, e);
  d.extend(f, e);
  this._dataArea = d.innerRect(c);
  this._dataBackground && this._dataBackground.paint(a, this._dataArea);
  this.drawAxes(a, b, this._dataArea);
  a.setHint("clip", this._dataArea);
  a.setHint("glass", this._dataArea);
  a.beginGroup("dataArea");
  b = this._renderer.passCount();
  for (c = 0;c < b;c++) {
    for (d = 0;d < this._dataset.rowCount();d++) {
      for (e = 0;e < this._dataset.columnCount();e++) {
        this._renderer.drawItem(a, this._dataArea, this, this._dataset, d, e, c);
      }
    }
  }
  a.endGroup();
};
jsfc.CategoryPlot.prototype.dataArea = function() {
  return this._dataArea;
};
jsfc.CategoryPlot.prototype.drawAxes = function(a, b, c) {
  var d = this._axisOffsets.value(this._xAxisPosition);
  this._xAxis.draw(a, this, b, c, d);
  d = this._axisOffsets.value(this._yAxisPosition);
  this._yAxis.draw(a, this, b, c, d);
};
jsfc.CategoryPlot.prototype.axisPosition = function(a) {
  if (a === this._xAxis) {
    return this._xAxisPosition;
  }
  if (a === this._yAxis) {
    return this._yAxisPosition;
  }
  throw Error("The axis does not belong to this plot.");
};
jsfc.CategoryPlot.prototype.legendInfo = function() {
  var a = [], b = this;
  this._dataset.rowKeys().forEach(function(c) {
    var d = b._dataset.rowIndex(c), d = b._renderer.getLineColorSource().getLegendColor(d), d = new jsfc.LegendItemInfo(c, d);
    d.label = c;
    a.push(d);
  });
  return a;
};
jsfc.XYPlot = function(a) {
  if (!(this instanceof jsfc.XYPlot)) {
    throw Error("Use 'new' for construction.");
  }
  this._listeners = [];
  this._notify = !0;
  this._plotBackground = null;
  this._dataBackground = new jsfc.StandardRectanglePainter(new jsfc.Color(230, 230, 230), new jsfc.Color(0, 0, 0, 0));
  this._dataArea = new jsfc.Rectangle(0, 0, 0, 0);
  this._renderer = new jsfc.ScatterRenderer(this);
  this._axisOffsets = new jsfc.Insets(0, 0, 0, 0);
  this._xAxis = new jsfc.LinearAxis;
  this._xAxisPosition = jsfc.RectangleEdge.BOTTOM;
  this._xAxis.configureAsXAxis(this);
  this._xAxisListener = function(a) {
    return function(c) {
      c.isAutoRange() && c.configureAsXAxis(a);
      a.notifyListeners();
    };
  }(this);
  this._xAxis.addListener(this._xAxisListener);
  this._yAxis = new jsfc.LinearAxis;
  this._yAxisPosition = jsfc.RectangleEdge.LEFT;
  this._yAxis.configureAsYAxis(this);
  this._yAxisListener = function(a) {
    return function(c) {
      c.isAutoRange() && c.configureAsYAxis(a);
      a.notifyListeners();
    };
  }(this);
  this._yAxis.addListener(this._yAxisListener);
  this.setDataset(a);
  this._staggerRendering = !1;
  this._drawMS = 150;
  this._pauseMS = 100;
  this._staggerID = null;
  this._progressColor1 = new jsfc.Color(100, 100, 200, 200);
  this._progressColor2 = new jsfc.Color(100, 100, 100, 100);
  this._progressLabelFont = new jsfc.Font("sans-serif", 12);
  this._progressLabelColor = jsfc.Colors.WHITE;
  this._progressLabelFormatter = new jsfc.NumberFormat(0);
};
jsfc.XYPlot.prototype.getDataset = function() {
  return this._dataset;
};
jsfc.XYPlot.prototype.setDataset = function(a, b) {
  this._datasetListener && this._dataset.removeListener(this._datasetListener);
  this._dataset = a;
  this._datasetListener = function(a) {
    return function(b) {
      a.datasetChanged();
    };
  }(this);
  this._dataset.addListener(this._datasetListener);
  this._xAxis.configureAsXAxis(this);
  this._yAxis.configureAsYAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.getBackground = function() {
  return this._plotBackground;
};
jsfc.XYPlot.prototype.setBackground = function(a, b) {
  this._plotBackground = a;
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.setBackgroundColor = function(a, b) {
  var c = new jsfc.StandardRectanglePainter(a, null);
  this.setBackground(c, b);
};
jsfc.XYPlot.prototype.getDataBackground = function() {
  return this._dataBackground;
};
jsfc.XYPlot.prototype.setDataBackground = function(a, b) {
  this._dataBackground = a;
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.setDataBackgroundColor = function(a, b) {
  var c = new jsfc.StandardRectanglePainter(a, null);
  this.setDataBackground(c, b);
};
jsfc.XYPlot.prototype.getRenderer = function() {
  return this._renderer;
};
jsfc.XYPlot.prototype.getStaggerRendering = function() {
  return this._staggerRendering;
};
jsfc.XYPlot.prototype.setStaggerRendering = function(a) {
  this._staggerRendering = a;
  this.notifyListeners();
};
jsfc.XYPlot.prototype.getDrawMillis = function() {
  return this._drawMS;
};
jsfc.XYPlot.prototype.setDrawMillis = function(a) {
  this._drawMS = a;
};
jsfc.XYPlot.prototype.getPauseMillis = function() {
  return this._pauseMS;
};
jsfc.XYPlot.prototype.setPauseMillis = function(a) {
  this._pauseMS = a;
};
jsfc.XYPlot.prototype.getProgressColor1 = function() {
  return this._progressColor1;
};
jsfc.XYPlot.prototype.setProgressColor1 = function(a) {
  this._progressColor1 = a;
};
jsfc.XYPlot.prototype.getProgressColor2 = function() {
  return this._progressColor2;
};
jsfc.XYPlot.prototype.setProgressColor2 = function(a) {
  this._progressColor2 = a;
};
jsfc.XYPlot.prototype.getProgressLabelFont = function() {
  return this._progressLabelFont;
};
jsfc.XYPlot.prototype.setProgressLabelFont = function(a) {
  this._progressLabelFont = a;
};
jsfc.XYPlot.prototype.getProgressLabelColor = function() {
  return this._progressLabelColor;
};
jsfc.XYPlot.prototype.setProgressLabelColor = function(a) {
  this._progressLabelColor = a;
};
jsfc.XYPlot.prototype.getProgressLabelFormatter = function() {
  return this._progressLabelFormatter;
};
jsfc.XYPlot.prototype.setProgressLabelFormatter = function(a) {
  this._progressLabelFormatter = a;
};
jsfc.XYPlot.prototype.datasetChanged = function() {
  this._xAxis.isAutoRange() && this._xAxis.configureAsXAxis(this);
  this._yAxis.isAutoRange() && this._yAxis.configureAsYAxis(this);
  this.notifyListeners();
};
jsfc.XYPlot.prototype.getAxisOffsets = function() {
  return this._axisOffsets;
};
jsfc.XYPlot.prototype.setAxisOffsets = function(a, b) {
  this._axisOffsets = a;
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.getXAxis = function() {
  return this._xAxis;
};
jsfc.XYPlot.prototype.setXAxis = function(a, b) {
  this._xAxis.removeListener(this._xAxisListener);
  this._xAxis = a;
  this._xAxis.addListener(this._xAxisListener);
  this._xAxis.configureAsXAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.getXAxisPosition = function() {
  return this._xAxisPosition;
};
jsfc.XYPlot.prototype.setXAxisPosition = function(a, b) {
  this.xAxisPosition = a;
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.isXZoomable = function() {
  return!0;
};
jsfc.XYPlot.prototype.getYAxis = function() {
  return this._yAxis;
};
jsfc.XYPlot.prototype.setYAxis = function(a, b) {
  this._yAxis.removeListener(this._yAxisListener);
  this._yAxis = a;
  this._yAxis.addListener(this._yAxisListener);
  this._yAxis.configureAsYAxis(this);
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.getYAxisPosition = function() {
  return this._yAxisPosition;
};
jsfc.XYPlot.prototype.setYAxisPosition = function(a, b) {
  this.yAxisPosition = a;
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.isYZoomable = function() {
  return!0;
};
jsfc.XYPlot.prototype.zoomXAboutAnchor = function(a, b, c) {
  var d = this._dataArea.minX(), e = this._dataArea.maxX();
  b = this._xAxis.coordinateToValue(b, d, e);
  this._xAxis.resizeRange(a, b, !1 !== c);
};
jsfc.XYPlot.prototype.zoomX = function(a, b, c) {
  this._xAxis.setBoundsByPercent(a, b, c);
};
jsfc.XYPlot.prototype.zoomYAboutAnchor = function(a, b, c) {
  var d = this._dataArea.minY(), e = this._dataArea.maxY();
  b = this._yAxis.coordinateToValue(b, e, d);
  this._yAxis.resizeRange(a, b, !1 !== c);
};
jsfc.XYPlot.prototype.zoomY = function(a, b, c) {
  this._yAxis.setBoundsByPercent(a, b, c);
};
jsfc.XYPlot.prototype.panX = function(a, b) {
  this._xAxis.pan(a, !1 !== b);
};
jsfc.XYPlot.prototype.panY = function(a, b) {
  this._yAxis.pan(a, !1 !== b);
};
jsfc.XYPlot.prototype.setRenderer = function(a, b) {
  this._renderer = a;
  !1 !== b && this.notifyListeners();
};
jsfc.XYPlot.prototype.draw = function(a, b, c) {
  this._staggerID && (clearTimeout(this._staggerID), a.setHint("layer", "progress"), a.clear(), a.setHint("layer", "default"));
  this._plotBackground && this._plotBackground.paint(a, c);
  var d = new jsfc.AxisSpace(0, 0, 0, 0), e = this.axisPosition(this._xAxis), f = this._xAxis.reserveSpace(a, this, b, c, e);
  d.extend(f, e);
  f = d.innerRect(c);
  e = this.axisPosition(this._yAxis);
  f = this._yAxis.reserveSpace(a, this, b, f, e);
  d.extend(f, e);
  this._dataArea = d.innerRect(c);
  this._dataBackground && this._dataBackground.paint(a, this._dataArea);
  this.drawAxes(a, b, this._dataArea);
  this._staggerRendering ? this._renderDataItemsByChunks(a) : this._renderAllDataItems(a);
};
jsfc.XYPlot.prototype._renderAllDataItems = function(a) {
  a.setHint("clip", this._dataArea);
  a.setHint("glass", this._dataArea);
  a.beginGroup("dataArea");
  a.save();
  a.setClip(this._dataArea);
  for (var b = this._renderer.passCount(), c = 0;c < b;c++) {
    for (var d = 0;d < this._dataset.seriesCount();d++) {
      for (var e = 0;e < this._dataset.itemCount(d);e++) {
        this._renderer.drawItem(a, this._dataArea, this, this._dataset, d, e, c);
      }
    }
  }
  a.restore();
  a.endGroup();
};
jsfc.XYPlot.prototype._renderDataItemsByChunks = function(a) {
  if (400 >= jsfc.XYDatasetUtils.itemCount(this._dataset)) {
    this._renderAllDataItems(a);
  } else {
    var b = {series:0, item:0};
    this._processChunk(a, this, 400, b);
    this._processChunkAndSubmitAnother(a, this, 200, b);
  }
};
jsfc.XYPlot.prototype._processChunk = function(a, b, c, d) {
  a.setHint("clip", this._dataArea);
  a.setHint("glass", this._dataArea);
  a.beginGroup("chunk");
  for (var e = b.getDataset(), f = !0, g = 0;g < c && f;g++) {
    b._renderer.drawItem(a, b._dataArea, b, e, d.series, d.item, 0), f = b._advanceCursor(d, e);
  }
  a.endGroup();
};
jsfc.XYPlot.prototype._processChunkAndSubmitAnother = function(a, b, c, d) {
  this._staggerID = setTimeout(function(a, b, c, d) {
    return function() {
      var k = Date.now();
      a._processChunk(b, a, c, d);
      k = Date.now() - k;
      d.series !== a._dataset.seriesCount() ? (c *= a._drawMS / k, a._processChunkAndSubmitAnother(b, a, c, d)) : (b.setHint("layer", "progress"), b.clear(), b.setHint("layer", "default"));
    };
  }(b, a, c, d), b._pauseMS);
  this._drawProgressIndicator(a, b.dataArea(), d, b.getDataset());
};
jsfc.XYPlot.prototype._drawProgressIndicator = function(a, b, c, d) {
  a.setHint("layer", "progress");
  a.clear();
  var e = jsfc.XYDatasetUtils.itemCount(this._dataset), f = this._itemsProcessed(c, this._dataset);
  c = b.centerX();
  var g = b.maxY() - 0.1 * b.height();
  d = b.width() / 1.2;
  b = this._progressLabelFont.size + 4;
  var e = f / e, f = c - d / 2, g = g - b / 2, h = c + d / 2;
  d = f + d * e;
  a.setFillColor(this._progressColor1);
  a.fillRect(f, g, d - f, b);
  a.setFillColor(this._progressColor2);
  a.fillRect(d, g, h - d, b);
  a.setFillColor(this._progressLabelColor);
  a.setFont(this._progressLabelFont);
  b = this._progressLabelFormatter.format(100 * e) + "%";
  a.drawAlignedString(b, c, g, jsfc.TextAnchor.TOP_CENTER);
  a.setHint("layer", "default");
};
jsfc.XYPlot.prototype._itemsProcessed = function(a, b) {
  for (var c = a.item, d = 0;d < a.series;d++) {
    c += b.itemCount(d);
  }
  return c;
};
jsfc.XYPlot.prototype._advanceCursor = function(a, b) {
  var c = b.itemCount(a.series);
  if (a.item === c - 1) {
    c = b.seriesCount();
    if (a.series === c - 1) {
      return a.series++, !1;
    }
    a.series++;
    a.item = 0;
    return!0;
  }
  a.item++;
  return!0;
};
jsfc.XYPlot.prototype.dataArea = function() {
  return this._dataArea;
};
jsfc.XYPlot.prototype.drawAxes = function(a, b, c) {
  var d = this._axisOffsets.value(this._xAxisPosition);
  this._xAxis.draw(a, this, b, c, d);
  d = this._axisOffsets.value(this._yAxisPosition);
  this._yAxis.draw(a, this, b, c, d);
};
jsfc.XYPlot.prototype.axisPosition = function(a) {
  if (a === this._xAxis) {
    return this._xAxisPosition;
  }
  if (a === this._yAxis) {
    return this._yAxisPosition;
  }
  throw Error("The axis does not belong to this plot.");
};
jsfc.XYPlot.prototype.legendInfo = function() {
  var a = [], b = this;
  this._dataset.seriesKeys().forEach(function(c) {
    var d = b._dataset.seriesIndex(c), d = b._renderer.getLineColorSource().getLegendColor(d), d = new jsfc.LegendItemInfo(c, d);
    d.label = c;
    a.push(d);
  });
  return a;
};
jsfc.XYPlot.prototype.findNearestDataItem = function(a, b, c, d) {
  c = c || 1;
  d = d || 1;
  for (var e = Number.MAX_VALUE, f, g = 0;g < this._dataset.seriesCount();g++) {
    for (var h = 0;h < this._dataset.itemCount(g);h++) {
      var k = this._dataset.item(g, h), l = k.x, k = k.y;
      this._xAxis.contains(l) && this._yAxis.contains(k) && (l = (a - l) / c, k = (b - k) / d, l = Math.sqrt(l * l + k * k), l < e && (f = {seriesKey:this._dataset.seriesKey(g), itemKey:this._dataset.itemKey(g, h)}, e = l));
    }
  }
  return f;
};
jsfc.XYPlot.prototype.addListener = function(a) {
  this._listeners.push(a);
};
jsfc.XYPlot.prototype.notifyListeners = function() {
  if (this._notify) {
    var a = this;
    this._listeners.forEach(function(b) {
      b(a);
    });
  }
};
jsfc.XYPlot.prototype.getNotify = function() {
  return this._notify;
};
jsfc.XYPlot.prototype.setNotify = function(a) {
  (this._notify = a) && this.notifyListeners();
};
jsfc.Modifier = function(a, b, c, d) {
  if (!(this instanceof jsfc.Modifier)) {
    throw Error("Use 'new' for constructor.");
  }
  this.altKey = a || !1;
  this.ctrlKey = b || !1;
  this.metaKey = c || !1;
  this.shiftKey = d || !1;
};
jsfc.Modifier.prototype.match = function(a, b, c, d) {
  return this.altKey === a && this.ctrlKey === b && this.metaKey === c && this.shiftKey === d;
};
jsfc.Modifier.prototype.matchEvent = function(a) {
  return this.match(a.altKey, a.ctrlKey, a.metaKey, a.shiftKey);
};
jsfc.Modifier.prototype.matches = function(a) {
  return this.altKey !== a.altKey || this.ctrlKey !== a.ctrlKey || this.metaKey !== a.metaKey || this.shiftKey !== a.shiftKey ? !1 : !0;
};
jsfc.MouseHandler = function() {
  throw Error("Documents an interface only.");
};
jsfc.MouseHandler.prototype.mouseDown = function(a) {
};
jsfc.MouseHandler.prototype.mouseMove = function(a) {
};
jsfc.MouseHandler.prototype.mouseUp = function(a) {
};
jsfc.MouseHandler.prototype.mouseOver = function(a) {
};
jsfc.MouseHandler.prototype.mouseOut = function(a) {
};
jsfc.MouseHandler.prototype.mouseWheel = function(a) {
};
jsfc.MouseHandler.prototype.cleanUp = function() {
};
jsfc.BaseMouseHandler = function(a, b, c) {
  if (!(this instanceof jsfc.BaseMouseHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  c || (c = this);
  jsfc.BaseMouseHandler.init(a, b, c);
};
jsfc.BaseMouseHandler.init = function(a, b, c) {
  c._manager = a;
  c._modifier = b || new jsfc.Modifier;
};
jsfc.BaseMouseHandler.prototype.getModifier = function() {
  return this._modifier;
};
jsfc.BaseMouseHandler.prototype.mouseDown = function(a) {
};
jsfc.BaseMouseHandler.prototype.mouseMove = function(a) {
};
jsfc.BaseMouseHandler.prototype.mouseUp = function(a) {
};
jsfc.BaseMouseHandler.prototype.mouseOver = function(a) {
};
jsfc.BaseMouseHandler.prototype.mouseOut = function(a) {
};
jsfc.BaseMouseHandler.prototype.mouseWheel = function(a) {
};
jsfc.BaseMouseHandler.prototype.cleanUp = function() {
};
jsfc.ClickSelectionHandler = function(a) {
  if (!(this instanceof jsfc.ClickSelectionHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseMouseHandler.init(a, null, this);
  this._extendModifier = new jsfc.Modifier(!1, !1, !1, !0);
  this._startPoint = null;
};
jsfc.ClickSelectionHandler.prototype = new jsfc.BaseMouseHandler;
jsfc.ClickSelectionHandler.prototype.mouseDown = function(a) {
  var b = this._manager.getElement().getBoundingClientRect();
  this._startPoint = new jsfc.Point2D(a.clientX - b.left, a.clientY - b.top);
};
jsfc.ClickSelectionHandler.prototype.mouseUp = function(a) {
  var b = this._manager.getElement().getBoundingClientRect();
  if (2 >= this._startPoint.distance(a.clientX - b.left, a.clientY - b.top)) {
    var b = this._manager.getChart().getPlot().getDataset(), c = a.target;
    if (c) {
      if (c = c.getAttribute("jfree:ref")) {
        var d = JSON.parse(c), c = d[0], d = d[1], e = b.isSelected("selection", c, d);
        this._extendModifier.matchEvent(a) || b.clearSelection("selection");
        e ? b.unselect("selection", c, d) : b.select("selection", c, d);
      } else {
        this._extendModifier.matchEvent(a) || b.clearSelection("selection");
      }
    }
  }
};
jsfc.LogEventHandler = function() {
  if (!(this instanceof jsfc.LogEventHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  this.modifier = new jsfc.Modifier;
  this._log = !1;
};
jsfc.LogEventHandler.prototype.mouseDown = function(a) {
  this._log && console.log("DOWN: clientX = " + a.clientX + ", y = " + a.clientY);
};
jsfc.LogEventHandler.prototype.mouseMove = function(a) {
  this._log && console.log("MOVE: clientX = " + a.clientX + ", y = " + a.clientY);
};
jsfc.LogEventHandler.prototype.mouseUp = function(a) {
  this._log && console.log("UP: clientX = " + a.clientX + ", y = " + a.clientY);
};
jsfc.LogEventHandler.prototype.mouseOver = function(a) {
  this._log && console.log("OVER: clientX = " + a.clientX + ", y = " + a.clientY);
};
jsfc.LogEventHandler.prototype.mouseOut = function(a) {
  this._log && console.log("OUT: clientX = " + a.clientX + ", y = " + a.clientY);
};
jsfc.LogEventHandler.prototype.mouseWheel = function(a) {
  if (!this._log) {
    return!1;
  }
  console.log("WHEEL : " + a.wheelDelta);
  return!1;
};
jsfc.PanHandler = function(a, b) {
  if (!(this instanceof jsfc.PanHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseMouseHandler.init(a, b, this);
  this._lastPoint = null;
};
jsfc.PanHandler.prototype = new jsfc.BaseMouseHandler;
jsfc.PanHandler.prototype.mouseDown = function(a) {
  var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left;
  a = a.clientY - b.top;
  this._lastPoint = this._manager.getChart().plotArea().constrainedPoint(c, a);
};
jsfc.PanHandler.prototype.mouseMove = function(a) {
  if (null !== this._lastPoint) {
    var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left, d = a.clientY - b.top, b = c - this._lastPoint.x();
    a = d - this._lastPoint.y();
    if (0 !== b || 0 !== a) {
      this._lastPoint = new jsfc.Point2D(c, d), c = this._manager.getChart().getPlot(), d = c.dataArea(), b = -b / d.width(), a /= d.height(), c.panX(b, !1), c.panY(a);
    }
  }
};
jsfc.PanHandler.prototype.mouseUp = function(a) {
  this._lastPoint = null;
  this._manager._liveMouseHandler = null;
};
jsfc.PolygonSelectionHandler = function(a, b) {
  if (!(this instanceof jsfc.PolygonSelectionHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseMouseHandler.init(a, b, this);
  this._extendModifier = new jsfc.Modifier(!1, !1, !1, !0);
  this._polygon = null;
  this._fillColor = new jsfc.Color(255, 255, 100, 100);
  this._lineStroke = new jsfc.Stroke(0.5);
  this._lineStroke.setLineDash([3, 3]);
  this._lineColor = jsfc.Colors.RED;
};
jsfc.PolygonSelectionHandler.prototype = new jsfc.BaseMouseHandler;
jsfc.PolygonSelectionHandler.prototype.mouseDown = function(a) {
  var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left;
  a = a.clientY - b.top;
  c = this._manager.getChart().getPlot().dataArea().constrainedPoint(c, a);
  this._polygon = new jsfc.Polygon;
  this._polygon.add(c);
};
jsfc.PolygonSelectionHandler.prototype.mouseMove = function(a) {
  if (null !== this._polygon) {
    var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left;
    a = a.clientY - b.top;
    c = this._manager.getChart().getPlot().dataArea().constrainedPoint(c, a);
    5 < this._polygon.getLastVertex().distance(c.x(), c.y()) && (this._polygon.add(c), 2 < this._polygon.getVertexCount() && (c = this._manager.getContext(), c.setHint("layer", "polygon"), c.clear(), c.setFillColor(this._fillColor), c.setLineColor(this._lineColor), c.setLineStroke(this._lineStroke), this._setPathFromPolygon(c, this._polygon), c.stroke(), c.setHint("layer", "default")));
  }
};
jsfc.PolygonSelectionHandler.prototype.mouseUp = function(a) {
  this._manager.getElement().getBoundingClientRect();
  var b = this._manager.getChart().getPlot();
  b.setNotify(!1);
  var c = b.getDataset();
  this._extendModifier.matchEvent(a) || c.clearSelection("selection");
  a = b.getXAxis();
  for (var d = b.getYAxis(), e = b.dataArea(), f = 0;f < c.seriesCount();f++) {
    for (var g = c.seriesKey(f), h = 0;h < c.itemCount(f);h++) {
      var k = c.item(f, h), l = a.valueToCoordinate(k.x, e.minX(), e.maxX()), k = d.valueToCoordinate(k.y, e.maxY(), e.minY());
      this._polygon.contains(new jsfc.Point2D(l, k)) && c.select("selection", g, c.itemKey(f, h));
    }
  }
  b.setNotify(!0);
  b = this._manager.getContext();
  b.setHint("layer", "polygon");
  b.clear();
  b.setHint("layer", "default");
  this._polygon = null;
  this._manager._liveMouseHandler = null;
};
jsfc.PolygonSelectionHandler.prototype._setPathFromPolygon = function(a, b) {
  a.beginPath();
  var c = b.getFirstVertex();
  a.moveTo(c.x(), c.y());
  for (var d = b.getVertexCount(), e = 1;e < d - 1;e++) {
    var f = b.getVertex(e);
    a.lineTo(f.x(), f.y());
  }
  a.lineTo(c.x(), c.y());
};
jsfc.XYCrosshairHandler = function(a) {
  if (!(this instanceof jsfc.XYCrosshairHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseMouseHandler.init(a, null, this);
  this._manager = a;
  this._layerID = "XYCrosshair";
  this._xCrosshair = new jsfc.Crosshair(Number.NaN);
  this._xLabelGenerator = new jsfc.StandardXYLabelGenerator("{X}", 4, 4);
  this._xFormatter = new jsfc.NumberFormat(4);
  this._yCrosshair = new jsfc.Crosshair(Number.NaN);
  this._yLabelGenerator = new jsfc.StandardXYLabelGenerator("{Y}", 4, 4);
  this._yFormatter = new jsfc.NumberFormat(4);
  this._snapToItem = !0;
};
jsfc.XYCrosshairHandler.prototype = new jsfc.BaseMouseHandler;
jsfc.XYCrosshairHandler.prototype.getXCrosshair = function() {
  return this._xCrosshair;
};
jsfc.XYCrosshairHandler.prototype.getXLabelGenerator = function() {
  return this._xLabelGenerator;
};
jsfc.XYCrosshairHandler.prototype.setXLabelGenerator = function(a) {
  this._xLabelGenerator = a;
};
jsfc.XYCrosshairHandler.prototype.getYCrosshair = function() {
  return this._yCrosshair;
};
jsfc.XYCrosshairHandler.prototype.getYLabelGenerator = function() {
  return this._yLabelGenerator;
};
jsfc.XYCrosshairHandler.prototype.setYLabelGenerator = function(a) {
  this._yLabelGenerator = a;
};
jsfc.XYCrosshairHandler.prototype.getSnapToItem = function() {
  return this._snapToItem;
};
jsfc.XYCrosshairHandler.prototype.setSnapToItem = function(a) {
  this._snapToItem = a;
};
jsfc.XYCrosshairHandler.prototype.mouseMove = function(a) {
  var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left, d = a.clientY - b.top, e = this._manager.getChart().getPlot();
  a = e.dataArea();
  var f = a.minX(), b = a.minY(), g = a.maxX(), h = a.maxY(), k = this._manager.getContext();
  if (a.contains(c, d)) {
    k.setHint("layer", this._layerID);
    k.clear();
    var l = e.getDataset(), m = e.getXAxis(), n = m.coordinateToValue(c, f, g), p = e.getYAxis(), r = p.coordinateToValue(d, h, b), s, q;
    if (this._snapToItem) {
      c = m.length() / a.width();
      s = p.length() / a.height();
      q = e.findNearestDataItem(n, r, c, s);
      var d = l.seriesIndex(q.seriesKey), t = l.itemIndex(q.seriesKey, q.itemKey), c = l.x(d, t), c = m.valueToCoordinate(c, f, g);
      s = this._xLabelGenerator.itemLabel(l, q.seriesKey, q.itemKey);
      e = e.getDataset().y(d, t);
      d = p.valueToCoordinate(e, h, b);
      q = this._yLabelGenerator.itemLabel(l, q.seriesKey, q.itemKey);
    }
    s = s || this._xFormatter.format(n);
    q = q || this._yFormatter.format(r);
    this._xCrosshair && (this._xCrosshair.setLabel(s), this._xCrosshair.drawVertical(k, c, a));
    this._yCrosshair && (this._yCrosshair.setLabel(q), this._yCrosshair.drawHorizontal(k, d, a));
  } else {
    k.setHint("layer", this._layerID), k.clear();
  }
  k.setHint("layer", "default");
};
jsfc.XYCrosshairHandler.prototype.cleanUp = function() {
  var a = this._manager.getContext();
  a.setHint("layer", this._layerID);
  a.clear();
  a.setHint("layer", "default");
};
jsfc.WheelHandler = function(a, b) {
  if (!(this instanceof jsfc.WheelHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseMouseHandler.init(a, b, this);
  this.manager = a;
  this.modifier = b || new jsfc.Modifier(!1, !1, !1, !1);
};
jsfc.WheelHandler.prototype = new jsfc.BaseMouseHandler;
jsfc.WheelHandler.prototype.mouseWheel = function(a) {
  var b;
  b = a.wheelDelta ? a.wheelDelta / 720 * -0.2 + 1 : 0.05 * a.detail + 1;
  var c = this.manager.getChart().getPlot(), d = c.isXZoomable(), e = c.isYZoomable(), f = this.manager.getElement();
  d && c.zoomXAboutAnchor(b, a.clientX - f.getBoundingClientRect().left, !e);
  e && c.zoomYAboutAnchor(b, a.clientY - f.getBoundingClientRect().top);
  (d || e) && a.preventDefault();
};
jsfc.ZoomHandler = function(a, b) {
  if (!(this instanceof jsfc.ZoomHandler)) {
    throw Error("Use 'new' for constructor.");
  }
  jsfc.BaseMouseHandler.init(a, b || null, this);
  this.zoomRectangle = this.zoomPoint = null;
  this._fillColor = new jsfc.Color(255, 0, 0, 50);
};
jsfc.ZoomHandler.prototype = new jsfc.BaseMouseHandler;
jsfc.ZoomHandler.prototype.mouseDown = function(a) {
  var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left;
  a = a.clientY - b.top;
  this.zoomPoint = this._manager.getChart().getPlot().dataArea().constrainedPoint(c, a);
};
jsfc.ZoomHandler.prototype.mouseMove = function(a) {
  if (null !== this.zoomPoint) {
    var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left;
    a = a.clientY - b.top;
    var d = this._manager.getChart().getPlot().dataArea().constrainedPoint(c, a), c = this._manager.getContext();
    c.setHint("layer", "zoom");
    c.clear();
    a = this.zoomPoint.x();
    var b = this.zoomPoint.y(), e = d.x() - a, d = d.y() - b;
    0 < e && 0 < d && (c.setFillColor(this._fillColor), c.setLineStroke(new jsfc.Stroke(0.1)), c.drawRect(a, b, e, d));
    c.setHint("layer", "default");
  }
};
jsfc.ZoomHandler.prototype.mouseUp = function(a) {
  if (null !== this.zoomPoint) {
    var b = this._manager.getElement().getBoundingClientRect(), c = a.clientX - b.left, b = a.clientY - b.top, d = this._manager.getChart().getPlot();
    a = d.dataArea();
    var b = a.constrainedPoint(c, b), e = this.zoomPoint.x(), c = this.zoomPoint.y(), f = b.x() - e, g = b.y() - c;
    if (0 < f && 0 < g) {
      var b = d.getXAxis(), d = d.getYAxis(), h = (e - a.minX()) / a.width(), e = (e + f - a.minX()) / a.width(), f = (a.maxY() - c) / a.height();
      a = (a.maxY() - c - g) / a.height();
      b.setBoundsByPercent(h, e, !1);
      d.setBoundsByPercent(a, f);
    } else {
      -2 > f && -2 > g && (d.getXAxis().setAutoRange(!0), d.getYAxis().setAutoRange(!0));
    }
    this.zoomPoint = null;
    a = this._manager.getContext();
    a.setHint("layer", "zoom");
    a.clear();
    a.setHint("layer", "default");
    this._manager._liveMouseHandler = null;
  }
};
jsfc.KeyedValueLabels = function() {
  if (!(this instanceof jsfc.KeyedValueLabels)) {
    return new jsfc.KeyedValueLabels;
  }
  this.format = "{K} = {V}";
  this.percentDP = this.valueDP = 2;
};
jsfc.KeyedValueLabels.prototype.itemLabel = function(a, b) {
  var c = new String(this.format), d = a.key(b), e = a.valueByIndex(b), f = e.toFixed(this.valueDP), g = a.total(), e = (e / g * 100).toFixed(this.percentDP), c = c.replace(/{K}/g, d), c = c.replace(/{V}/g, f);
  return c = c.replace(/{P}/g, e);
};
jsfc.KeyedValue2DLabels = function() {
  if (!(this instanceof jsfc.KeyedValue2DLabels)) {
    return new jsfc.KeyedValue2DLabels;
  }
  this.format = "{R}, {C} = {V}";
  this.valueDP = 2;
};
jsfc.KeyedValue2DLabels.prototype.itemLabel = function(a, b, c) {
  var d = new String(this.format), e = a.rowKey(b), f = a.columnKey(c);
  a = a.valueByIndex(b, c).toFixed(this.valueDP);
  d = d.replace(/{R}/g, e);
  d = d.replace(/{C}/g, f);
  return d = d.replace(/{V}/g, a);
};
jsfc.KeyedValue3DLabels = function() {
  if (!(this instanceof jsfc.KeyedValue3DLabels)) {
    return new jsfc.KeyedValue3DLabels;
  }
  this.format = "{S}, {R}, {C} = {V}";
  this.valueDP = 2;
};
jsfc.KeyedValue3DLabels.prototype.itemLabel = function(a, b, c, d) {
  var e = new String(this.format), f = a.seriesKey(b), g = a.rowKey(c), h = a.columnKey(d);
  a = a.valueByIndex(b, c, d).toFixed(this.valueDP);
  e = e.replace(/{S}/g, f);
  e = e.replace(/{R}/g, g);
  e = e.replace(/{C}/g, h);
  return e = e.replace(/{V}/g, a);
};
jsfc.XYLabelGenerator = function() {
  throw Error("Documents an interface only.");
};
jsfc.XYLabelGenerator.prototype.itemLabel = function(a, b, c) {
};
jsfc.StandardXYLabelGenerator = function(a, b, c) {
  if (!(this instanceof jsfc.StandardXYLabelGenerator)) {
    throw Error("Use 'new' with constructor.");
  }
  this._format = a || "{X}, {Y} / {S}";
  this._xDP = b || 2;
  this._yDP = c || 2;
};
jsfc.StandardXYLabelGenerator.prototype.itemLabel = function(a, b, c) {
  var d = new String(this._format);
  c = a.itemByKey(b, c);
  a = c.x.toFixed(this._xDP);
  c = c.y.toFixed(this._yDP);
  d = d.replace(/{X}/g, a);
  d = d.replace(/{Y}/g, c);
  return d = d.replace(/{S}/g, b);
};
jsfc.XYZLabels = function() {
  if (!(this instanceof jsfc.XYZLabels)) {
    return new jsfc.XYZLabels;
  }
  this.format = "{X}, {Y}, {Z} / {S}";
  this.zDP = this.yDP = this.xDP = 2;
};
jsfc.XYZLabels.prototype.itemLabel = function(a, b, c) {
  var d = new String(this.format), e = a.seriesIndex(b), e = a.item(e, c);
  a = e.x.toFixed(this.xDP);
  c = e.y.toFixed(this.yDP);
  e = e.z.toFixed(this.zDP);
  d = d.replace(/{X}/g, a);
  d = d.replace(/{Y}/g, c);
  d = d.replace(/{Z}/g, e);
  return d = d.replace(/{S}/g, b);
};

