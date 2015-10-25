<?php
class Document {

    const CONTENT_COL = 6;
    const TITLE_COL = 3;

    private $user;

    private $name;

    public function __construct($name, User $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function get_user(){

        return $this->user;
    }

    public function get_name(){

        $this->name;
    }

    public function getTitle() {

        return $this->get_attribute(self::TITLE_COL); // third column in a row
    }

    public function getContent() {

        return $this->get_attribute(self::CONTENT_COL);
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

    private function get_attribute($column_number){
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[$column_number]; // third column in a row
    }
}

class User {

    public function makeNewDocument($name) {
        $doc = new Document($name, $this);

        return $doc;
    }

    public function getMyDocuments() {
        $list = array();
        $all_docs = Document::getAllDocuments();
        foreach ($all_docs as $doc) {
            if ($doc->get_user() == $this)
                $list[] = $doc;
        }
        return $list;
    }
}
