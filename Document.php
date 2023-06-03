<?php
class Document {

    private $user;
    private $name;
    private $title;
    private $content;

    public function setUser($user) {
      $this->$user = $user;
    }

    public function getUser() {
      return $this->$user;
    }

    public function setName($name) {
      $this->$name = $name;
    }

    public function getName() {
      return $name->$name;
    }

    public function setTitle($title) {
      $this->$title = $title;
    }

    public function getTitle() {
      return $title->$title;
    }

    public function setContent($content) {
      $this->$content = $content;
    }

    public function getContent() {
      return $this->$content;
    }

    public function init($name, User $user) {

        assert(strlen($name) > 5);

        $this->user = $user;
        $this->name = $name;

        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');

        if (!empty($row)) {
            $this->title = $row["title"];
            $this->content = $row["content"];
        }

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
            if ($doc->getUser() == $this)
                $list[] = $doc;
        }

        return $list;
    }
}
