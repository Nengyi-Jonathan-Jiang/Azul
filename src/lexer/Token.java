package lexer;

public class Token{
    public final String type, value;
    public final int index;
    public Token(String type, String value, int index){
        this.type = type;
        this.value = value;
        this.index = index;
    }
    public String toString(){
        return this.type.equals(this.value) ? this.value : this.type + "<" + this.value + ">";
    }
    public boolean isEndToken(){
        return this.type.equals("__END__") || this.value.equals("__END__");
    }
}