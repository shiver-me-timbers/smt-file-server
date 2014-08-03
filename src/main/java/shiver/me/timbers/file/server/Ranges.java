package shiver.me.timbers.file.server;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses a Range header ({@code Range: bytes=0-499,500-999,1000-1499}) value into a list of {@link Range}s.
 *
 * @author Karl Bennett
 */
public class Ranges extends AbstractList<Range> {

    private static final String BYTES = "bytes=";

    private final List<Range> ranges;

    public Ranges() {
        this(BYTES, 0);
    }

    /**
     * @param rangeHeaderValue must be in the format "bytes=(\d*-\d*)*" where at least one of the numbers on either side
     *                         in each range is present.
     * @param fileSize         the size of the file that the range is being requested for.
     */
    public Ranges(String rangeHeaderValue, long fileSize) {

        rangeHeaderValueShouldBeValid(rangeHeaderValue, fileSize);

        final String rangeValue = rangeHeaderValue.replace(BYTES, "");
        final String[] rangeStrings = rangeValue.split(",");

        this.ranges = new ArrayList<>(rangeStrings.length);

        if (noRangesSupplied(rangeStrings)) {
            return;
        }

        for (String rangeString : rangeStrings) {

            this.ranges.add(new Range(rangeString, fileSize));
        }
    }

    private static void rangeHeaderValueShouldBeValid(String rangeHeaderValue, long fileSize) {

        if (!rangeHeaderValue.contains(BYTES)) {
            throw new RequestedRangeNotSatisfiableException(rangeHeaderValue, fileSize);
        }
    }

    private static boolean noRangesSupplied(String[] rangeStrings) {
        return 1 == rangeStrings.length && "".equals(rangeStrings[0]);
    }

    @Override
    public Range get(int index) {
        return ranges.get(index);
    }

    @Override
    public int size() {
        return ranges.size();
    }
}