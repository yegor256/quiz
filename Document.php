<?php
class Document {

    const NAME_INDEX = 1;
    const TITLE_INDEX = 3;
    const CONTENT_INDEX = 6;

    private $user;
    private $name;
    private $title;
    private $content;

    public static function makeDocument($name, User $user) {
        assert(strlen($name) > 5);
        $db = Database::getInstance();
        $row = $db->insert('insert statement');
        return new Document($row, $user);
    }

    public static function getDocument($name, User $user) {
        assert(strlen($name) > 5);
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $name . '" LIMIT 1');
        return new Document($row, $user);
    }

    public static function getAllDocuments(User $user = null){
        $condition = '';
        $documents = [];

        if ($user != null) {
            $condition = ' where userId = ' . $user->getId();
        }
        $db = Database::getInstance();
        $rows = $db->query('SELECT * FROM document ' . $condition);
        foreach($rows as $row) {
            $documents[] = new Document($row, $user);
        }
        return $documents;
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
}

class User {

    private $id;
    private $name;

    public function __construct($id, $name) {
        $this->id = $id;
        $this->name = $name;
    }

    public function getId() {
        return $this->id;
    }

    public function getName() {
        return $this->name;
    }

    public function makeNewDocument($name) {
        $doc = Document::makeDocument($name, $this);
        return $doc;
    }

    public function getDocument($name) {
        $doc = Document::getDocument($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        $documents = Document::getAllDocuments($this);
        return $documents;
    }

}
