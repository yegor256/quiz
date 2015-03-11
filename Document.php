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
        $doc = $this->getDocument();
        return $doc['title'];
    }

    public function getContent() {
        $doc = $this->getDocument();
        return $doc['content'];
    }

    public static function getAllDocuments() {
        $db = Database::getInstance();
        $rows = $db->query('SELECT * FROM document');
        return $rows;
    }

    private function getDocument() {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . mysqli_real_escape_string($this->name) . '" LIMIT 1');
        return $row;
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
            if ($doc->user == $this) {
                $list[] = $doc;
            }
        }
        return $list;
    }

}
