public interface Text {
    /**
     *
      * @return content of the text
     */
    String content();

    /**
     *
     * @param content save content operation to some persistence.
     */
    void saveContent(String content);
}
