<?php

namespace quiz;

use quiz\User as User;
// use common\Database as Database;

/**
 *
 * @author teamed, smasala
 *        
 */
class Document {
	
	/**
	 *
	 * @var User
	 */
	public $user;
	
	/**
	 *
	 * @var string
	 */
	public $name;
	
	/**
	 * 
	 * @param string $name
	 * @param User $user
	 * @throws \Exception
	 */
	public function __construct ($name, User $user){
		if (! assert ( strlen ( $name ) > 5 )) {
			throw new \Exception ( "name bust me 6 or more characters long" );
		}
		$this->user = $user;
		$this->name = $name;
	}
	
	/**
	 * Get Document Column
	 * 
	 * @param string $prop
	 *        	- column name
	 * @return unknown
	 */
	protected function getDocumentColumn($prop) {
		$db = Database::getInstance ();
		$row = $db->query ( 'SELECT "' . $prop . '" FROM document WHERE name = "' . $this->name . '" LIMIT 1' );
		return $row [0];
	}
	
	/**
	 * Get Document Title
	 *
	 * @return string
	 */
	public function getTitle() {
		return $this->getDocumentColumn ( "title" );
	}
	
	/**
	 * Get Document Content
	 *
	 * @return text
	 */
	public function getContent() {
		return $this->getDocumentColumn ( "content" );
	}
	
	/**
	 * Get All Documents
	 *
	 * @return list[Document]
	 */
	public static function getAllDocuments() {
		// to be implemented later
	}
}
