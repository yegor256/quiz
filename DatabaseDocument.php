<?php

final class DatabaseDocument implements Document {

    private $name;

    /** @var  Database */
    private $database;

    public function __construct($name, Database $database) {
        $this->name = $name;
        $this->database = $database;
    }

    public function getContent()
    {
        return $this->database->query('SELECT content FROM document WHERE name = "' . $this->name . '" LIMIT 1')['content'];
    }

    public function getTitle() {
        return $this->database->query('SELECT title FROM document WHERE name = "' . $this->name . '" LIMIT 1')['title'];
    }
}
