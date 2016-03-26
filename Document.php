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
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[3]; // third column in a row
    }

    public function getContent() {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[6]; // sixth column in a row
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}


}
