public class GlossaryItem
{
    private String phrase;
    private String definition;

    public GlossaryItem(String phrase, String definition)
    {
        if (phrase == null || phrase.isEmpty())
        {
            throw new IllegalArgumentException("Empty phrase");
        }
        if (definition == null || definition.isEmpty())
        {
            definition = "[No definition]";
        }
        this.phrase = phrase;
        this.definition = definition;
    }

    public String getPhrase()
    {
        return phrase;
    }

    public String getDefinition()
    {
        return definition;
    }

    public void setDefinition(String description)
    {
        this.definition = description;
    }

    @Override
    public String toString()
    {
        return String.format("%s: \"%s\"", phrase, definition);
    }
}
