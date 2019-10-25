<?php
class User {

    private $id;

    public function __construct($id) {
        $this->id = $id;
    }
    
    public function createDocument($name) {
        return new Document($name, $this);
    }

    public function getMyDocuments() {
        $db = Database::getInstance();
        $list = array();
        foreach ($db->query('SELECT name FROM document WHERE user_id = ' . $this->id) as $name) {
            $list[] = $this->createDocument($name);
        }
        return $list;
    }

}
