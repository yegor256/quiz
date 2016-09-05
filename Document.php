<?php
/**
 * The Document file.
 *
 * PHP version 5
 * 
 * I seperate Document.php into two file, 
 * so each class has it's own file
 *
 * @category  Category
 * @package   Package
 * @author    Display Name <username@example.com>
 * @copyright 2016-2017 Foo Inc.
 * @license   Foo License
 * @link      Link
 */

/**
 * The Document class.
 *
 * @category Category
 * @package  Package
 * @author   Display Name <username@example.com>
 * @license  Foo License
 * @link     Link
 */
class Document
{
    public $user;

    public $name;

    public $database;

    /**
     * The constructor
     *
     * I think constructor is better than init,
     * and better to make static initialization of
     * database as constructor's argument
     *
     * @param string $name     Name parameter
     * @param object $user     User parameter
     * @param object $database Database parameter
     *
     * @return void
     */
    public function __construct($name, User $user, Database $database) 
    {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
        // set database here, so it can be use throughout the class
        $this->database = $database;
    }

    /**
     * Get document title.
     *
     * @return string document title
     */
    public function title() 
    {
        // move query to different line, so that the next line isn't too long
        $sql = 'SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1';
        $row = $this->database->query($sql);
        return $row[3]; // third column in a row
    }

    /**
     * Get document content.
     *
     * @return string document content
     */
    public function content() 
    {
        $sql = 'SELECT * FROM document WHERE name = "' . $this->name . '" LIMIT 1';
        $row = $this->database->query($sql);
        return $row[6]; // sixth column in a row
    }

    /**
     * Get all document.
     *
     * @return array all documents
     */
    public function all() 
    {
        // to be implemented later
    }

}