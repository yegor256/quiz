<?php
class Document {

    //Variables should be private
    private $user;

    private $name;

    public function init($name, User $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle($db) {
        //It would be better to use database as dependency injection, singleton or static is accepted
        //$db = Database::getInstance();

        //This query can be SQL Injected, prepared statements could be a great option, altought direct concatenation of values can be acceptable.

        //No exception handling or verification if values could be obtained or not.
        try{
            $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
            //Was the value obtained correctly? Check here
            return $row[3]; // third column in a row
        }catch(exception $ex){
            echo $ex->getMessage();
        }
    }


    public function getContent($db) {
        //It would be better to use database as dependency injection
        //$db = Database::getInstance();

        try{
        $row = $db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[6]; // sixth column in a row
        }catch (Exception $ex){
            echo $ex->getMessage();
        }
    }

    public static function getAllDocuments() {
        // to be implemented later
    }

}

class User {

    public function makeNewDocument($name) {
        $doc = new Document();
        $doc->init($name, $this);
        return $doc;
    }

    public function getMyDocuments() {
        $list = array();
        foreach (Document::getAllDocuments() as $doc) {
            if ($doc->user == $this)
                $list[] = $doc;
        }
        return $list;
    }

}