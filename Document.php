<?php
class Document {

    public $user;

    public $name;

    public function init($name, User $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle() {
        $db = Database::getInstance();
        $row = $db->query('SELECT title FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[0];
    }

    public function getContent() {
        $db = Database::getInstance();
        $row = $db->query('SELECT content FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[0]; 
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}

class User {

    public function makeNewDocument($name) {
        $doc = new Document();
        $doc->init($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        $list = array();
        foreach (Document::getAllDocuments() as $doc) {
            if ($doc->user == $this)
                $list[] = $doc;
        }
        return $list;
    }

}
