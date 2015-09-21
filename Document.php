<?php
class Document {

    public $user;
    public $name;
    public $title;
    public $content;

    public function init($name, User $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
        
        $db = Database::getInstance();
        $row = $db->query('SELECT title, content FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        
        if ($row) {
        	$this->title = $row["title"];
			$this->content = $row["content"];
        }
    }

    public static function getAllDocuments($user) {
	    // get documents by email
        // to be implemented later
    }

}

class User {
	
	public $email;
	
    public function makeNewDocument($name) {
        $doc = new Document();
        $doc->init($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        $list = array();
        foreach (Document::getAllDocuments($this) as $doc) {
        	array_push($list, $doc)
        }
        return $list;
    }

}
