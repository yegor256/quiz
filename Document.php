<?php

/**
* Document class
*/
class Document {

    public $user;

    public $name;
	
	private $db;

	/**
	* Initializes the document.
	*/
    public function init($name, User $user) {
		// Amend assertaition with if - assert should be used only in debugging. See: http://php.net/manual/en/function.assert.php
		if(strlen($name) < 5){
			$this->user = $user;
			$this->name = $name;
			$this->db = Database::getInstance();
		} else {
			// TODO: Review and discuss, not sure what is the expected behaviour here.
			die("Name must be longer than 5 characters");
		}
    }

	/**
	* Gets the title of the current document.
	* 
	* TODO: bad method name. This returns the first document title of the user. Not surely the one that he/she has added first, but the first
	* one that the db engine fids. This needs clarification - what do we really need here?
	*/ 
    public function getTitle() {
		// This looks very unsafe from SQL injection point of view. This definitely should be removed, and replaced with MqSQLi prepared statement.
        $row = $this->db->query('SELECT title FROM document WHERE name = "' . $this->name . '" LIMIT 1');
		
		// TODO: clarify the destionation php version. In 5.6+, this couldld be only 1 line:
		// return $this->db->query('SELECT title FROM document WHERE name = "' . $this->name . '" LIMIT 1')[0];
		// But older versions needs the variable to be declared.
        return $row[0];
    }

	/**
	* Gets the content of the current document.
	* 
	* TODO: Review, same points as above in getTitle()
	*/ 
    public function getContent() {
		// Same as above in getTitle()
        $row = $this->db->query('SELECT content FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[0]; 
    }
	
	/**
	* Gets the title of the current document.
	* Not implemented yet.
	*/ 
    public static function getAllDocuments() {
        // TODO: to be implemented later
    }
	
	// TODO: Implelemt getAllDocumentsByUser()
}

/**
* User class
* TODO: Should be moved to a separate file.
*/
class User {

	/**
	* Creates a new document.
	*/
    public function makeNewDocument($name) {
        $doc = new Document();
        $doc->init($name, $this);
        return $doc;
    }

	/**
	* Gets back the documents of the given user.
	*/
    public function getMyDocuments() {
        $list = array();
				
		// TODO: This definitely should not be done trough Document::getAllDocuments(). There should be a method to get back the 
		// documents of the current user. In that case, the loop isn't even needed.
        foreach (Document::getAllDocuments() as $doc) {
            if ($doc->user == $this) {
                array_push($list $doc);
			}
        }
        return $list;
    }
}
