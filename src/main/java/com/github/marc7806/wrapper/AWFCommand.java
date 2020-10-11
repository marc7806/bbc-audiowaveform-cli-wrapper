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
    private final ImmutableList.Builder<String> args = new ImmutableList.Builder<>();

    private File input;
    private File output;
    private Integer zoom;
    private AWFBit bits;
    private Boolean splitChannels;
    private Integer start;
    private Integer width;
    private Integer height;
    private AWFColorSchema colorSchema;
    private Boolean hideAxisLabels;
    private String amplitudeScale;

    @Override
    public List<String> getArguments() {
        checkNotNull(this.input, "Input file can not be null");
        checkNotNull(this.output, "Output file can not be null");

        this.args.add("-i", this.input.getAbsolutePath());
        this.args.add("-o", this.output.getAbsolutePath());

        if (this.zoom != null) {
            this.args.add("-z", String.valueOf(this.zoom));
        }
        if (this.bits != null) {
            this.args.add("-b", this.bits.getVal());
        }
        if (this.start != null) {
            this.args.add("-s", String.valueOf(this.start));
        }
        if (this.width != null) {
            this.args.add("-w", String.valueOf(this.width));
        }
        if (this.height != null) {
            this.args.add("-h", String.valueOf(this.height));
        }
        if (this.colorSchema != null) {
            this.args.add("--colors", this.colorSchema.getVal());
        }
        if (this.amplitudeScale != null) {
            this.args.add("--amplitude-scale", this.amplitudeScale);
        }
        if (this.splitChannels != null) {
            this.args.add("--split-channels");
        }
        if (this.hideAxisLabels != null) {
            this.args.add("--no-axis-labels");
        }

        return this.args.build();
    }

    /**
     * @return a {@link AWFCommandBuilder} object.
     */
    public static AWFCommandBuilder builder() {
        return new AWFCommandBuilder();
    }

    public static class AWFCommandBuilder {
        private final ImmutableList.Builder<String> args = new ImmutableList.Builder<>();
        private final AWFCommand awfCommand = new AWFCommand();

        /**
         * Input filename, which should be a MP3, WAV, FLAC, or Ogg Vorbis audio file, or a binary waveform data file
         * By default, audiowaveform uses the file extension to decide how to read the input file
         *
         * @param input
         *         Input file
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder setInput(File input) {
            this.awfCommand.input = checkNotNull(input);
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
            this.awfCommand.output = checkNotNull(output);
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
            this.awfCommand.zoom = zoom;
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
            this.awfCommand.bits = bits;
            return this;
        }

        /**
         * Output files are multi-channel, not combined into a single waveform
         *
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder splitChannels() {
            this.awfCommand.splitChannels = true;
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
            this.awfCommand.start = start;
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
            this.awfCommand.width = width;
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
            this.awfCommand.height = height;
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
            this.awfCommand.colorSchema = colorSchema;
            return this;
        }

        /**
         * When creating a waveform image, specifies whether to hide axis labels
         *
         * @return AWFCommandBuilder
         */
        public AWFCommandBuilder hideAxisLabels() {
            this.awfCommand.hideAxisLabels = true;
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
            this.awfCommand.amplitudeScale = amplitudeScale;
            return this;
        }

        public AWFCommand build() {
            return awfCommand;
        }
    }
}
