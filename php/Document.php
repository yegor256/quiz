<?php

class Document {

    /**
     * User of Document.
     *
     * @var User
     */
    public $user;

    /**
     * Name of Document.
     *
     * @var string
     */
    public $name;

    /**
     * Constructor
     *
     * @return void
     */
    public function __construct()
    {
        $this->db = Database::getInstance();
    }

    /**
     * Instantiates a new Document with a given name and User.
     *
     * @param  string $name Name.
     * @param  User   $user User
     *
     * @return void
     */
    public function init($name, User $user)
    {
        if (!strlen($name) > 5)) {
            throw new Exception("Name is too short.");
        };
        $this->user = $user;
        $this->name = $name;
    }

    /**
     * Title of the current Document.
     *
     * @return string
     */
    public function getTitle(): string
    {
        $row = $this->db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[3]; // third column in a row
    }

    /**
     * Content of the current Document.
     *
     * @return string
     */
    public function getContent(): string
    {
        $row = $this->db->query('SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1');
        return $row[6]; // sixth column in a row
    }

    /**
     * Returns all Documents.
     * 
     * @return Document[]
     */
    public static function getAllDocuments(): array
    {
        // to be implemented later
    }

}
