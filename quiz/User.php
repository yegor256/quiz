<?php

namespace quiz;

use quiz\Document as Document;

/**
 *
 * @author teamed, smasala
 *        
 */
class User {
	
	/**
	 *
	 * @param string $name        	
	 * @throws \ErrorException
	 * @return \quiz\Document
	 */
	public function makeNewDocument($name) {
		return new Document ($name, $this);
	}
	
	/**
	 *
	 * @return list[quiz\Document]
	 */
	public function getMyDocuments() {
		$list = array ();
		foreach ( Document::getAllDocuments () as $doc ) {
			if ($doc->getUser() == $this)
				$list [] = $doc;
		}
		return $list;
	}
}