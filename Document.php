<?php
class Document {

    private $name;

    private $user;

    private $content;

    private $title;

    public function __construct($name, User $user) {
        $this->setName($name);
        $this->setUser($user);
        
        $this->init($name);
    }

    public function init($name) {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');

        $this->name = $name;
        $this->content = $row['content'];
        $this->title = $row['title'];
    }

    public function getUser() {
        return $this->user;
    }

    public function setUser(User $user) {
        $this->user = $user;
    }

    public function getName() {
        return $this->name;
    }

    public function setName($name) {
        assert(strlen($name) > 5);
        $this->name = $name;
    }

    public function getTitle() {
        return $this->title;
    }

    public function getContent() {
        return $this->content;
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}

class User {

    public function makeNewDocument($name) {
        return new Document($name, $this);
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
