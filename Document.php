<?php
class Document
{
    public $user;
    public $name;

    public function __construct($name, User $user)
    {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle()
    {
        $row = Database::getInstance()->query("SELECT * FROM document WHERE name = '$this->name' LIMIT 1");
        return $row[3]; // third column in a row
    }

    public function getContent()
    {
        $row = Database::getInstance()->query("SELECT * FROM document WHERE name = '$this->name' LIMIT 1");
        return $row[6]; // sixth column in a row
    }

    public static function getAllDocuments()
    {
        return Database::getInstance()->query("SELECT * FROM document WHERE name = '$this->name'"); // all column in a row
    }
}

class User
{
    public function makeNewDocument($name)
    {
        return new Document($name, $this);
    }

    public function getMyDocuments()
    {
        $list = array();
        foreach (Document::getAllDocuments() as $doc)
        {
            if($doc->user == $this)$list[] = $doc;
        }
        return $list;
    }
}