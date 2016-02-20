<?php
class Document {

    const TITLE_INDEX = 3;
    const CONTENT_INDEX = 6;

    public $user;

    public $name;

    public function init($name, User $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle() {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[self::TITLE_INDEX]; // third column in a row
    }

    public function getContent() {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[self::CONTENT_INDEX]; // sixth column in a row
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
