<?php
/**
 * The User file.
 *
 * PHP version 5
 *
 * @category  Category
 * @package   Package
 * @author    Display Name <username@example.com>
 * @copyright 2016-2017 Foo Inc.
 * @license   Foo License
 * @link      Link
 */

// Document File required to make new Document
require_once 'Document.php';

/**
 * The User class.
 *
 * @category Category
 * @package  Package
 * @author   Display Name <username@example.com>
 * @license  Foo License
 * @link     Link
 */
class UserDocument
{
    protected $document;

    /**
     * Better create constructor and pass Document
     * as constructor argument
     *
     * @param Document $document document parameter
     *
     * @return void
     */
    public function __construct(Document $document)
    {
        // set document here, so it can be use troughout the class
        $this->document = $document;
    }

    /**
     * Create user document.
     *
     * @param string $name Name parameter
     *
     * @return Document New Document
     */
    public function create($name) 
    {
        // use document constructor to create new document
        // instead of init method
        $document = new Document($name, $this, $this->document->database);
        return $document;
    }

    /**
     * List of user document.
     *
     * @return array list of my documents
     */
    public function listOf() 
    {
        $list = array();
        foreach ($this->document->all() as $doc) {
            if ($doc->user == $this) {
                $list[] = $doc; 
            }
        }
        return $list;
    }

}
