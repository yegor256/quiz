<?php

class Document {

    private $user;
    private $name;
    
    const TITLE_COL_NUM = 3;
    const CONTENT_COL_NUM = 6; 

    public function __construct($name, User $user) {
        if (strlen((string) $name) <= 5) {
            throw new InvalidArgumentException();
        }
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle() {
        return $this->getParam(self::TITLE_COL_NUM);
    }

    public function getContent() {
        return $this->getParam(self::CONTENT_COL_NUM);
    }

    public function getParam($colNum) {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . mysql_real_escape_string($this->name) . '" LIMIT 1');
        return $row[$colNum]; // sixth column in a row
    }
    public static function getAllDocuments() {
        // to be implemented later
        return array();
    }
    
    public function getUser() {
        return $this->user;
    }
    
    public function getName() {
        return $this->name;
    }

}

class User {

    public function makeNewDocument($name) {
        $doc = new Document($name, $this);
        return $doc;
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
