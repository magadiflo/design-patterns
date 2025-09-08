package dev.magadiflo.patterns.plainjava.creational.builder.classic.product;

// 1. PRODUCTO (Report)
public class Report {

    private String header;
    private String content;
    private String footer;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Report{");
        sb.append("header='").append(header).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", footer='").append(footer).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
