<?php
class Document {

    public $user;
    public $name;
    public $data;   // this associative array contains database data

    function __construct($name, User $user) {
        //  populate the data property with all fieds from the database table
        sanity_check($name, $user);
        $this->name = $name;
        $this->user = $user;
        $this->data = $this->hydrate($name);
    }

    protected function sanity_check($name, User $user) {
        assert( strlen($name) > 5, 'document name must be at least 5 characters long' );
    }

    protected function hydrate($name) {
        //  return all document fields using a prepared statement
        $db = Database::getInstance();
        $statement = $db->prepare('SELECT * FROM document WHERE name = ?');
        $statement->bind_param("s", $name);
        $statement->execute();
        $result = $statement->get_result();
        $fields = $result->fetch_assoc();
        $statement->close();
        $result->free();
        $db->close();
        //  throw an error if document cannot be found
        if (is_null($fields)) {
            throw new Excection('Document ' . $name . ' does not exist');
        }
        return $fields;
    }

    public function getAllByUser($user) {
        $docs = [];
        $db = Database::getInstance();
        $statement = $db->prepare('SELECT * FROM document WHERE user = ?');
        $statement->bind_param("s", $user->name);
        $statement->execute();
        $result = $statement->get_result();
        while ($doc = $result->fetch_assoc()) {
            $docs[] = $doc;
        }
        $result->free();
        $statement->close();
        $db->close();
        return $docs;
    }

    //  these are now just helper functions, because all heavy lifting was done during instantiation
    public function getTitle() {
        return $this->data->title;
    }

    public function getContent() {
        return $this->data->content;
    }

}

class User {

    public $name;

    function __construct($name) {
        $this->name = $name;
    }

    public function makeNewDocument($name) {
        return new Document($name,$this);
    }

    public function getMyDocuments() {
        return Document::getAllByUser($this);
    }

}
