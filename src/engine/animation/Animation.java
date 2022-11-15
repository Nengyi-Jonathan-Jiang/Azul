package engine.animation;

import lexer.Lexer;
import lexer.Token;

import java.io.InputStream;
import java.util.*;

public class Animation {
    public final AnimationFrame[] frames;
    public final int width, height, duration;
    public final AnimationFrame startFrame;
    public final AnimationFrame endFrame;

    private Animation(AnimationFrame[] frames, AnimationFrame startFrame, AnimationFrame endFrame){
        this.startFrame = startFrame;
        this.endFrame = endFrame;

        this.frames = frames;

        int duration = 0;

        width = frames[0].width;
        height = frames[0].height;

        for(AnimationFrame frame : frames){
            if(frame.width != width || frame.height != height){
                throw new Error("Animation error: Animation frames must be the same size");
            }
            duration += frame.duration;
        }

        this.duration = duration;
    }

    private static final Map<String, Animation> cachedAnimations = new TreeMap<>();
    private static final Lexer animationFileLexer;

    static {
        animationFileLexer = new Lexer();
        animationFileLexer.addRule("defaultDuration", "defaultDuration");
        animationFileLexer.addRule("frame", "frame");
        animationFileLexer.addRule("start", "startFrame");
        animationFileLexer.addRule("end", "endFrame");
        animationFileLexer.addRule("number", "\\d+");
        animationFileLexer.addRule("file", "[a-zA-Z0-9()_\\-,.]+|\"[a-zA-Z0-9()_\\-,. /\\\\]*\"");
    }

    /**
     * Reads an animation from an animation file <br>
     * animation file format:  <br>
     * &nbsp; &nbsp; [defaultDuration &lt;duration&gt;]  <br>
     * &nbsp; &nbsp; frame &lt;filePath&gt; [&lt;duration&gt;] ...
     */
    public static Animation getAnimationFromFile(String filePath){
        if(cachedAnimations.containsKey(filePath)) return cachedAnimations.get(filePath);

        List<AnimationFrame> frames = new ArrayList<>();

        InputStream fileInputStream = Animation.class.getResourceAsStream("/" + filePath);

        Scanner scan = new Scanner(fileInputStream);

        int defaultDuration = 1;
        AnimationFrame startFrame = null, endFrame = null;

        while(scan.hasNextLine()){
            String line = scan.nextLine();
            var lex = animationFileLexer.parse(line);

            Token tkn;
            if((tkn =lex.next()).isEndToken()) continue;

            switch (tkn.type) {
                case "defaultDuration" -> {
                    if((tkn =lex.next()).isEndToken() || !tkn.type.equals("number"))
                        throw new Error("AnimationFileError: Expected integer after defaultDuration declaration");
                    defaultDuration = Integer.parseInt(tkn.value);
                }
                case "frame" -> {
                    AnimationFrame frame = readFrame(lex, defaultDuration);
                    frames.add(frame);
                }
                case "start" -> {
                    if(startFrame != null){
                        throw new Error("Animation File Error: Cannot have multiple start frames");
                    }
                    startFrame = readFrame(lex, defaultDuration);
                }
                case "end" -> {
                    if(endFrame != null){
                        throw new Error("Animation File Error: Cannot have multiple end frames");
                    }
                    endFrame = readFrame(lex, defaultDuration);
                }
                default -> throw new Error("AnimationReaderError: Invalid line: \"" + line + "\"");
            }
        }

        if(frames.size() == 0){
            throw new Error("Animation error: Animation must have at least one frame");
        }

        if(startFrame == null) startFrame = frames.get(0);
        if(endFrame == null) endFrame = frames.get(frames.size() - 1);

        Animation res = new Animation(frames.toArray(AnimationFrame[]::new), startFrame, endFrame);
        cachedAnimations.put(filePath, res);
        return res;
    }

    private static AnimationFrame readFrame(Lexer.Lex lex, int defaultDuration){
        Token tkn;

        if((tkn = lex.next()).isEndToken() || !tkn.type.equals("file"))
            throw new Error("Expected file name after frame declaration but instead got " + tkn);

        String fileName = tkn.value;
        if(fileName.startsWith("\"")) fileName = fileName.substring(1, fileName.length() - 1);
        int duration = defaultDuration;

        if(!(tkn = lex.next()).isEndToken()) {
            if(tkn.type.equals("number"))
                duration = Integer.parseInt(tkn.value);
            else
                throw new Error("Expected number after file name but instead got " + tkn);
        }

        return new AnimationFrame(fileName, duration);
    }
}
