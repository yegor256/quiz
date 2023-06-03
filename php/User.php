<?php

class User
{

    /**
     * Creates a new Document with a given name.
     *
     * @param  string $name Name for the new Document.
     *
     * @return Document
     */
    public function makeNewDocument($name): Document
    {
        $doc = new Document();
        $doc->init($name, $this);
        return $doc;
    }

    /**
     * Returns all documents for the current user.
     *
     * @return Document[]
     */
    public function getMyDocuments(): array
    {
        array_filter(Document::getAllDocuments(), function($document) {
            return ($document->getUser() === $this);
        });
    }
}
