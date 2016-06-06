<?php
use documents as Document;

class User extends Document
{
    /**
     * Creates a new Document.
     *
     * @param  string $name Name for the new Document.
     *
     * @return Document
     */
    public function addDocument($name)
    {
        $doc = new Document($name, $this);
        return $doc;
    }
    /**
     * Returns all documents.
     *
     * @return Document[]
     */
    public function getMyDocuments()
    {
        return (Document::getAllDocuments());
    }
}
