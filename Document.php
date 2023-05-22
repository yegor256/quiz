<?php

class DocumentDAO {
    private  Database $database;

    public function __construct(Database $database)
    {
        $this->database = $database;
    }

    public function persistDocument(Document $document): bool
    {
        // persists $document in the database
    }

    public function getDocumentsByUser(User $user): array
    {
        $query = $this->database->prepareQuery('SELECT * FROM documents WHERE user_id = ?')->args($user->getId());
        $result = $this->database->exec($query);
        $documents = [];
        foreach ($result->rows as $row){
            $documents[] = new Document($row['name'], $user, $row['title'], $row['content']);
        }
        return $documents;
    }
}

class Document {

    public User $user;

    public string $name;

    public string $title;

    public string $content;

    public function __construct($name, User $user, $title, $content) {
        if (strlen($name) < 5) {
            throw new Exception("too short user name");
        }
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle() {

        return $this->title;
    }

    public function getContent() {
        return $this->content;
    }
}

class User {

    private DocumentDao $documentDAO;

    private $id;

    public function makeNewDocument($name, $content, $title): Document {
        $doc = new Document($name, $this, $title, $content);
        $this->documentDAO->persistDocument($doc);
        return $doc;
    }

    public function getId():int{
        return $this->id;
    }

    public function getMyDocuments() {
        $this->documentDAO->getDocumentsByUser($this);
    }

}
