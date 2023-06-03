<?php
class Document {

    public $user;
    public $name;
    protected $db;
    private $row;

    public function __construct()
    {
        # code...
    }

    public function init($name, User $user, Database $db) {
        $this->user = $user;
        $this->name = $name;
        $this->db = $db->getInstance();
    }

    public function save()
    {
        $query = 'INSERT INTO document (name, user_id) VALUES (?, ?)';
        return $this->db->query($query, $this->name, $this->user->id);
    }

    public function validate()
    {
        return assert(strlen($this->name) > 5);
    }

    public function getTitle() {
        $row = $this->getRow();
        return isset($row[2]) ? 
            $row[2] : // third column in a row
            throw new Exception('Unable to get the document\'s title');
    }

    public function getContent() {
        $row = $this->getRow();
        return isset($row[5]) ? 
            $row[5] : // sixth column in a row
            throw new Exception('Unable to get the document\'s content');
    }

    protected function getRow()
    {
        if (isset($this->row) and $this->row) {
            return $this->row;
        }
        $query = 'SELECT * FROM document WHERE name = ? LIMIT 1';
        $this->row = $this->db->query($query, $this->name); // Assuming prepared statements
        return $this->row;
    }

    public function getAll() {
        $query = 'SELECT * FROM document';
        $rows = $this->db->query($query);
        return count($rows) ? $rows : null;
    }

}

class User {

    protected $document;

    public function __construct(Document $document = null)
    {
        $this->document = $document ? $document : new Document;
    }

    public function makeNewDocument($name) {
        $doc = clone $this->document;
        $doc->init($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        $list = array();
        foreach ($this->document->getAll() as $doc) {
            if ($doc->user == $this)
                $list[] = $doc;
        }
        return $list;
    }

}
