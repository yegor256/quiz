<?php
class Document {

    private $user;

    private $name;

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

}
