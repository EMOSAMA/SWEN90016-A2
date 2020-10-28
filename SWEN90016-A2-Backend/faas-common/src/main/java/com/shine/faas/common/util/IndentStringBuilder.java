package com.shine.faas.common.util;

public class IndentStringBuilder {
    private StringBuilder builder = new StringBuilder();
    private Boolean newLine = true;
    private int indentCount;
    //private int LineCount;

    //region get set

    public int getLineCount() {

        int count = 0;
        for (int i = 0; i < this.builder.length(); i++) {
            if (this.builder.charAt(i) == '\n') {
                count++;
            }
        }
        return count;
    }


    public void setIndentCount(int indentCount) {
        this.indentCount = indentCount;
    }

    //endregion


    public int increase() {
        this.indentCount++;
        return this.indentCount;
    }

    public int increase(String val) {
        onNewLine(this.builder);
        this.builder.append(val);
        this.indentCount++;
        return this.indentCount;
    }

    public int decrease() {
        this.indentCount--;
        return this.indentCount;
    }

    public int decrease(String val) {
        this.indentCount--;
        onNewLine(this.builder);
        this.builder.append(val);
        return this.indentCount;
    }

    public IndentStringBuilder() {
        indentCount = 0;
    }

    public IndentStringBuilder(int indent) {
        this();
        this.indentCount = indent;
    }

    public void append(String value) {
        onNewLine(this.builder);
        builder.append(value);
    }

    public void appendLine() {
        //this.builder.append();
        this.builder.append('\n');
        newLine = true;
    }

    public void appendLine(String value) {
        onNewLine(this.builder).append(value).append('\n');
        newLine = true;
    }

    public void increaseIndentLine() {
        onNewLine(this.builder).append('\n');
        indentCount++;
        newLine = true;
    }

    public void increaseIndentLine(String value) {
        onNewLine(this.builder).append(value).append('\n');
        indentCount++;
        newLine = true;
    }

    public void increaseIndentFormatLine(String format, String... args) {
        onNewLine(this.builder).append(format(format, args));
        appendLine();
        indentCount++;
        newLine = true;
    }

    public void decreaseIndentLine() {
        indentCount--;
        onNewLine(this.builder);
        appendLine();
        newLine = true;
    }

    public void decreaseIndentLine(String value) {
        indentCount--;
        onNewLine(this.builder);
        appendLine(value);
        newLine = true;
    }

    private static String format(String fmt, String... args) {
        String result = fmt;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", args[i]);
        }
        return result;
    }

    public void appendFormat(String format, String... args) {
        onNewLine(this.builder).append(format(format, args));
    }

    public void increaseIndentFormat(String format, String... args) {
        onNewLine(this.builder).append(format(format, args));
        indentCount++;
    }

    public void decreaseIndentFormat(String format, String... args) {
        indentCount--;
        onNewLine(this.builder).append(format(format, args));
    }

    public void decreaseIndentFormatLine(String format, String... args) {
        indentCount--;
        onNewLine(this.builder).append(format(format, args));
        appendLine();
        newLine = true;
    }

    public void appendFormatLine(String format, String... args) {
        onNewLine(this.builder).append(format(format, args));
        appendLine();
        newLine = true;
    }

    public String getContent() {
        return this.builder.toString();
    }

    public void delete(int start, int end) {
        this.builder.delete(start, end);
    }

    public void deleteCharAt(int index) {
        this.builder.deleteCharAt(index);
    }

    private StringBuilder onNewLine(StringBuilder builder) {
        if (this.newLine) {
            for (int i = 0; i < indentCount; i++)
                builder.append("\t");
        }
        this.newLine = false;
        return builder;
    }

    public void deleteNewLine() {
        for (int i = 0; i < 5; i++) {
            String content = this.getContent();
            if (content.endsWith("\n"))
                this.builder.deleteCharAt(content.length() - 1);
            else
                break;
        }

        if(this.getContent().endsWith("\n"))
            System.out.println("lllll");
    }

//        public int Length
//        {
//            get
//            { return this.builder.Length; }
//        }
//
//        public void Clear()
//        {
//            this.builder.Clear();
//        }
}
