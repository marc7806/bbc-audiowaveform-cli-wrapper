package com.github.marc7806.wrapper;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.io.File;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Command that contains arguments for bbc-audiowaveform execution
 * Use ${@link AWFCommandBuilder} for building a ${@link AWFCommand}
 *
 * <pre>
 * <code>AWFCommand command = AWFCommand.builder()
 *      .setInput(new File(&quot;example-input.mp3&quot;))
 *      .setOutput(new File(&quot;example-output.json&quot;))
 *      .addZoom(256)
 *      .setBits(AWFBit.SIXTEEN)
 *      .build();</code>
 * </pre>
 *
 * @author Marc7806
 * @version 1.0
 */
public class AWFCommand implements Command {
    private ImmutableList.Builder<String> _args = new ImmutableList.Builder<>();
    private File _input;
    private File _output;
    private int _zoom;
    private AWFBit _bits;
    private boolean _splitChannels;
    private int _start;
    private int _width;
    private int _height;
    private AWFColorSchema _colorSchema;
    private boolean _hideAxisLabels;
    private String _amplitudeScale;

    @Override
    public List<String> getArguments() {
        checkNotNull(_input, "Input file can not be null");
        checkNotNull(_output, "Output file can not be null");
        return _args.build();
    }

    /**
     * @return a {@link AWFCommandBuilder} object.
     */
    public static AWFCommandBuilder builder() {
        return new AWFCommandBuilder();
    }

    public static class AWFCommandBuilder {
        private final ImmutableList.Builder<String> _args = new ImmutableList.Builder<>();
        private final AWFCommand _awfCommand = new AWFCommand();

        /**
         * Input filename, which should be a MP3, WAV, FLAC, or Ogg Vorbis audio file, or a binary waveform data file
         * By default, audiowaveform uses the file extension to decide how to read the input file
         *
         * @param input
         *         Input file
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setInput(File input) {
            _awfCommand._input = checkNotNull(input);
            _args.add("-i", input.getAbsolutePath());
            return this;
        }

        /**
         * Output filename, which may be either a WAV audio file, a binary or JSON format waveform data file, or a PNG image file
         * By default, audiowaveform uses the file extension to decide the kind of output to generate
         *
         * @param output
         *         Output file
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setOutput(File output) {
            _awfCommand._output = checkNotNull(output);
            _args.add("-o", output.getAbsolutePath());
            return this;
        }

        /**
         * Specifies the number of input samples to use to generate each output waveform data point
         *
         * @param zoom
         *         Number of input samples
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder addZoom(int zoom) {
            _awfCommand._zoom = zoom;
            _args.add("-z", String.valueOf(zoom));
            return this;
        }

        /**
         * Specifies the number of data bits to use for output waveform data points
         *
         * @param bits
         *         Number of bits
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setBits(AWFBit bits) {
            _awfCommand._bits = bits;
            _args.add("-b", bits.getVal());
            return this;
        }

        /**
         * Output files are multi-channel, not combined into a single waveform
         *
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder splitChannels() {
            _awfCommand._splitChannels = true;
            _args.add("--split-channels");
            return this;
        }

        /**
         * When creating a waveform image, specifies the start time, in seconds
         *
         * @param start
         *         Start time in seconds
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setStart(int start) {
            _awfCommand._start = start;
            _args.add("-s", String.valueOf(start));
            return this;
        }

        /**
         * When creating a waveform image, specifies the image width
         *
         * @param width
         *         Image width
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setWidth(int width) {
            _awfCommand._width = width;
            _args.add("-w", String.valueOf(width));
            return this;
        }

        /**
         * When creating a waveform image, specifies the image height
         *
         * @param height
         *         Image height
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setHeight(int height) {
            _awfCommand._height = height;
            _args.add("-h", String.valueOf(height));
            return this;
        }

        /**
         * When creating a waveform image, specifies the color scheme to use
         *
         * @param colorSchema
         *         Image color schema
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setColorSchema(AWFColorSchema colorSchema) {
            _awfCommand._colorSchema = colorSchema;
            _args.add("--colors", colorSchema.getVal());
            return this;
        }

        /**
         * When creating a waveform image, specifies whether to hide axis labels
         *
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder hideAxisLabels() {
            _awfCommand._hideAxisLabels = true;
            _args.add("--no-axis-labels");
            return this;
        }

        /**
         * When creating a waveform image or waveform data file, specifies an amplitude scaling (or vertical zoom) to apply to the waveform
         * Must be either a number or 'auto', which scales the waveform to the maximum height
         *
         * @param amplitudeScale
         *         Amplitude scaling to apply to waveform
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setAmplitudeScale(String amplitudeScale) {
            checkArgument(!amplitudeScale.isEmpty(), "Amplitude scale value can not be empty");
            _awfCommand._amplitudeScale = amplitudeScale;
            _args.add("--amplitude-scale", amplitudeScale);
            return this;
        }

        public AWFCommand build() {
            _awfCommand._args = _args;
            return _awfCommand;
        }
    }
}
