<?php
class User {

    public function makeNewDocument($name) {
        return new Document($name, $this);
    }

    public function getMyDocuments() {
        $db = Database::getInstance();
        $list = array();
        foreach ($db->query('SELECT name FROM document WHERE user_id = ' . $this->id) as $name) {
            $list[] = $this->makeNewDocument($name);
        }
        return $list;
    }

}
