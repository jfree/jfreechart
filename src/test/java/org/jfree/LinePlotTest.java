import org.jfree.data.statistics.LinePlotDataset;

public class LineChartTest {

    /** A chart. */
    private JFreeChart chart;

    /**
     * Common test setup.
     */
    @BeforeEach
    public void setUp() {
        this.chart = createLineChart();
    }

    /**
     * Check that setting a URL generator for a series does override the
     * default generator.
     */
    @Test
    public void testSetSeriesURLGenerator() {
        CategoryPlot<?, ?> plot = (CategoryPlot) this.chart.getPlot();
        CategoryItemRenderer renderer = plot.getRenderer();
        StandardCategoryURLGenerator url1 = new StandardCategoryURLGenerator();
        renderer.setSeriesItemURLGenerator(0, url1);
        CategoryURLGenerator url2 = renderer.getItemURLGenerator(0, 0);
        assertSame(url2, url1);
    }

    /**
     * Create a line chart with sample data in the range -3 to +3.
     *
     * @return The chart.
     */
    private static JFreeChart createLineChart() {
        Number[] data = new Integer[] { -3, -2, 0, 1, 3 };
        LinePlotDataset<Integer> dataset = DatasetUtils.createCategoryDataset("S", data);
        return ChartFactory.createLinePlot("Line Plot",
                dataset);
    }

}
