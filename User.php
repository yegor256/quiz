<?php

final class User {

    private $documents;

    public function addDocument(Document $document) {
        $this->documents[] = $document;
    }

    public function getAllDocuments() {
        return $this->documents;
    }
}
