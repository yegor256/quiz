<?php
class Document {

    public $user;

    public $name;

    public function __construct($name, User $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle() {
        return $this->getColumn(3); // third column in a row
    }

    public function getContent() {
        return $this->getColumn(6); // sixth column in a row
    }

    private function getColumn($column) {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[$column]; // sixth column in a row
    }

    public static function getUserDocuments(User $user) {
        // to be implemented later
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}

class User {

    public function makeNewDocument($name) {
        $doc = new Document($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        return Document::getUserDocuments($this);
    }

}
