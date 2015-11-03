<?php
class Document {

    private $id;
    private $user;

    public function __construct($id, User $user) {
        $this->id = $id;
        $this->user = $user;
    }

    public function getTitle() {
        return Database::getInstance()->query('SELECT title FROM document WHERE id = ?', [$this->id])[0];
    }

    public function getContent() {
        return Database::getInstance()->query('SELECT content FROM document WHERE id = ?', [$this->id])[0];
    }

    public function getUser()
    {
        return $this->user;
    }

    public static function create($name, User $user) {
        assert(strlen($name) > 5);
        return new self(
            Database::getInstance()->query(
                'INSERT INTO document (name, user_id) VALUES (?, ?, ?, ?) RETURNING id', 
                [$name, $user->getId()]
            )[0],
            $user
        );
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}

class User {

    public function makeNewDocument($name) {
        return Document::create($name, $this);
    }

    public function getMyDocuments() {
        $list = array();
        foreach (Document::getAllDocuments() as $doc) {
            if ($doc->getUser() == $this)
                $list[] = $doc;
        }
        return $list;
    }

}
