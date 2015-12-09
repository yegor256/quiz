<?php

class Document {

    /**
     * @var string
     */
    private $name;

    /**
     * @var Database
     */
    private $db;

    /**
     * @param $name
     * @param User $user
     */
    public function __construct($name){
        $this->name = $name;

        $this->assertDocumentName();
        $this->db = Database::getInstance();
    }

    /**
     * @return mixed
     */
    public function getTitle()
    {
        return $this->db->query('SELECT title FROM document WHERE name = "' . $this->name . '" LIMIT 1')[0];
    }

    /**
     * @return mixed
     */
    public function getContent()
    {
        return $this->db->query('SELECT content FROM document WHERE name = "' . $this->name . '" LIMIT 1')[0];
    }

    /**
     * Assert function
     */
    private function assertDocumentName(){
        assert(strlen($this->name) > 5);
    }

    /**
     * @return mixed
     */
    public static function getAllDocuments() {
        return $this->db->query('SELECT * FROM document"');
    }

}

class User {

    /**
     * @var int
     */
    private $id;

    /**
     * @return int
     */
    public function getId(){
        return $this->id;
    }

    /**
     * @param $name
     * @return Document
     */
    public function makeNewDocument($name)
    {
        return new Document($name);
    }

    /**
     * @return array
     */
    public function getMyDocuments()
    {
        $list = [];

        foreach (Document::getAllDocuments() as $doc)
            if ($doc->user->id == $this->getId())
                $list[] = $doc;

        return $list;
    }

}
