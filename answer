


<?php

/**
 * i'm gonna to illustrate my examp using mysql database and php so,
   first of all wee need to create procedures to being our data from the database


CREATE DEFINER=`root`@`localhost` PROCEDURE `getUserDocuments`(in p_name varchar(200),in p_limit int)
BEGIN

set @statment = concat("
SELECT * FROM document WHERE name = '",name,"'"," LIMIT ", p_limit);

prepare stat from @statment;
execute stat;
deallocate prepare stat;

END
 * 
 */
////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////

/**
 * important Notes : 
 * 1- this example i illustrate it with pure php code, but it's better to user php with
 *     codeIgniter or laravel.
 * 2- i can use oracle database instead of mysql if the system is sensitive.
 */

////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
class Document {
    /**
     *
     * @var object of user class 
     */
    private $user;
    
    /**
     *
     * @String name the name of the doucument 
     */
    private $name;
    
    /**
     * 
     * @param String $name the length of the name must be at least more than 5 character
     * @param User $user
     */
    /**
     *
     * @var database connection
     */
    private $connection;
    
    public function __construct() {
        $this->connection = new DataBase();
        
    }
    private function init($name, User $user) {
        assert(strlen($name) > 5);
        if(strlen($name)<=5){
            
       assert('the lenght of the name mutt be ate least mor then 5 character');
        }else{
        $this->user = $user;
        $this->name = $name;
        }
    }
    
    /**
     * 
     * @return String the title of the document
     */
    private function getTitle() {
        $db = $this->connection->getInstance();
        $row = $db->query('call getUserDocuments( "' . $this->name . '" , 1 )');
        $this->connection->close();
        return $row['title']; // the name of column
    }
    /**
     * 
     * @return String the content of the document
     */
    private function getContent() {
        $db = $this->connection->getInstance();
        $row = $db->query('call getUserDocuments( "' . $this->name . '" , 1 )');
        $this->connection->close();
        return $row['content']; // the name of column
        
    }
    /**
     * @return list of  all doucuments for all users 
     */
    public static function getAllDocuments() {
        // to be implemented later
    }
}
class User {
    
    /**
     *
     * @var String the name of the user or mapy some thing else to identify the each user, and it's must be unique
     */
    private $user_name;
    
    
    /**
     * make new document for specific user
     * @param String $name the name of the document
     * @return object of document class
     */
    private function makeNewDocument($name) {
        $doc = new Document();
        $doc->init($name, $this);
        return $doc;
    }
    /**
     * return list of specific usere documents
     * @return list of user documents
     */
    private function getMyDocuments() {
        $list = array();
        foreach (Document::getAllDocuments() as $doc) {
            if ($doc->user->user_name == $this->user_name)
                $list[] = $doc;
        }
        return $list;
    }
}
