<?php
class Document {

    // why to have the entire $user for a model like this. I prefer to use an id once we have a Model for User also

    // why public?
    private $user_id;
    private $name;

    // why not to make a constructor?
    function Document($name, $user_id) {
        assert(strlen($name) > 5);
        $this->user_id = $user_id;
        $this->name = $name;
    }

    // two problems I see with this query
    // 1. query the whole document to use only the title is not good
    // 2. using strings in WHERE clause is not good also
    // we could try to something like
    public function getTitle($doc_id) {
        $sql = 'SELECT title FROM document WHERE document.id = $doc_id LIMIT 1';
        $result = mysql_query($sql);
        return mysql_fetch_object($result);
    }

    // same here
    public function getContent($doc_id) {
        $sql = 'SELECT content FROM document WHERE document.id = $doc_id LIMIT 1';
        $result = mysql_query($sql);
        return mysql_fetch_object($result);
    }

    // this method could be usefull but not for the needs inside the class User (eg.: User::getMyDocuments())
    // my suggestion goes below
    public static function getAllDocuments() {
        // to be implemented later
    }

    // I think somethink like this is much better than to get all the documents inside the database and then 
    // check if each one is from a specific user 
    public function getDocumentsFromUser($user_id) {
        $list = array();
        // query only the documents of the specified user

        return $list;
    }

}

class User {

    private $id;
    private $user_name;
    // etc...

    // constructor

    //
    public function makeNewDocument($name) {
        $doc = new Document($name, $this->id);
        return $doc;
    }

    // to get only my documents is better to delegate to the Document class passing my user id
    public function getMyDocuments() {
        $list = Document::getDocumentsFromUser($this->id);
        return $list;
    }

}
