<?php
class Document {

    public $user_id;
    public $name;
    
    private $title;
    private $content;

    public function init($name, $user_id) {
        assert(strlen($name) > 5);
        $this->user_id = $user_id;
        $this->name = $name;
        
        $row = $this->searchByName($name);
        
        $this->title = $row[3];
        $this->content = $row[6];
        
    }

    public function getTitle() {
        return $this->title;
    }

    public function getContent() {
        return $this->content;
    }

    private function searchByName($name){
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . $name . '" LIMIT 1');
        
        return $row;
    }
    
    private function searchByUserId(){
        $db = Database::getInstance();
        $documents = $db->query(
            'SELECT * 
            FROM document d 
            JOIN document_user du ON d.id = du.document_id
            WHERE du.user_id = "$user_id" ');
        return $documents;
    }

}

class User {
    
    public $user_id;

    public function makeNewDocument($name) {
        $doc = new Document();
        $doc->init($name, $this->user_id);
        return $doc;
    }

    public function getMyDocuments() {
        return Document::searchByUser($this->user_id);
    }

}
