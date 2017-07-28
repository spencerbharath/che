package org.eclipse.che.ide.api.resources;

import org.eclipse.che.api.project.shared.SearchOccurrence;

/**
 * @author Vitalii Parfonov
 */

public class SearchOccurrenceImpl implements SearchOccurrence {

    private float  score;
    private int    endOffset;
    private int    startOffset;
    private String phrase;
    private String lineContent;
    private int lineNumber;

    public SearchOccurrenceImpl(SearchOccurrence searchOccurrence) {
        score = searchOccurrence.getScore();
        endOffset = searchOccurrence.getEndOffset();
        startOffset = searchOccurrence.getStartOffset();
        phrase = searchOccurrence.getPhrase();
        lineContent = searchOccurrence.getLineContent();
        lineNumber = searchOccurrence.getLineNumber();
    }

    public SearchOccurrenceImpl(float score,
                                int endOffset,
                                int startOffset,
                                String phrase,
                                String lineContent,
                                int lineNumber) {
        this.score = score;
        this.endOffset = endOffset;
        this.startOffset = startOffset;
        this.phrase = phrase;
        this.lineContent = lineContent;
        this.lineNumber = lineNumber;
    }

    @Override
    public float getScore() {
        return 0;
    }

    @Override
    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String getPhrase() {
        return phrase;
    }

    @Override
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    @Override
    public int getEndOffset() {
        return endOffset;
    }

    @Override
    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    @Override
    public int getStartOffset() {
        return startOffset;
    }

    @Override
    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    @Override
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public void setLineContent(String lineContent) {
        this.lineContent = lineContent;
    }

    @Override
    public String getLineContent() {
        return lineContent;
    }
}
