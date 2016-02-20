<?php
class Document {

    const NAME_INDEX = 1;
    const TITLE_INDEX = 3;
    const CONTENT_INDEX = 6;

    private $user;
    private $name;
    private $title;
    private $content;

    public static function getDocument($name, User $user) {
        assert(strlen($name) > 5);
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $name . '" LIMIT 1');
        return new Document($row, $user);
    }

    private function __construct($row, User $user) {
        $this->user = $user;
        if (empty($row)) {
            throw new Exception('Empty result set!');
        }
        $this->name = $row[self::NAME_INDEX];
        $this->title = $row[self::TITLE_INDEX];
        $this->content = $row[self::CONTENT_INDEX];
    }

    public function getUser() {
        return $this->user;
    }

    public function getName() {
        return $this->name;
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
        $doc = Document::getDocument($name, $this);
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
