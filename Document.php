<?php
class Document {
    const TITLE_COLUMN_NUMBER = 3;

    const CONTENT_COLUMN_NUMBER = 6;

    protected $user;

    protected $name;

    protected $dbInstance;

    public function __construct($name, User $user)
    {
        assert(strlen($name) > 5, new \Exception('Document name should be more be than 5 symbols'));
        $this->user = $user;
        $this->name = $name;
        $this->dbInstance = Database::getInstance();
    }

    public function getTitle() {
        $row = $this->dbInstance->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[self::TITLE_COLUMN_NUMBER] ?? '';
    }

    public function getContent() {
        $row = $this->dbInstance->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[self::CONTENT_COLUMN_NUMBER] ?? '';
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}

class User {
    public function makeNewDocument($name) {
        $doc = new Document($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        $list = [];
        foreach (Document::getAllDocuments() as $doc) {
            if ($doc->user == $this) {
                $list[] = $doc;
            }
        }
        return $list;
    }
}
