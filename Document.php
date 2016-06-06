<?php
namespace documents

class Document {
    /**
     * User of Document.
     *
     * @var User
     */
    private $user;
    /**
     * Name of Document.
     *
     * @var string
     */
    private $name;
    /**
     * Instantiates a new Document with a given name and User.
     *
     * @param  string $name Name.
     * @param  User   $user User
     *
     * @return void
     */
    public function __construct($name, User $user)
    {
        $this->setName($name);
        $this->setUser($user);
    }
    /**
     * Title of the current Document.
     *
     * @return string
     */
    public function getTitle()
    {
        return $this->title;
    }
    /**
     * Sets new Title for current Document.
     *
     * @param string $title
     *
     * @throws Exception Throws an Exception when name is too short.
     *
     * @return $this
     */
    public function setTitle(string $title)
    {
      if (!strlen($name) > 5) {
          throw new Exception("Name is too short.");
      }
      $this->title = $title;
      return $this;
    }
    /**
     * User of the current Document.
     *
     * @return User
     */
    public function getUser()
    {
        return $this->user;
    }
    /**
     * Sets User for current Document.
     *
     * @param User $user
     *
     * @return $this
     */
    public function setUser(User $user)
    {
      $this->user = $user;
      return $this;
    }
    /**
     * Content of the current Document.
     *
     * @return string
     */
    public function getContent()
    {
        return $this->content;
    }
    /**
     * Sets Content.
     *
     * @param string $content
     *
     * @return $this
     */
    public function setContent(string $content)
    {
      $this->content = $content;
      return $this;
    }
    /**
     * Fetches a Document.
     *
     * @param  string $name
     *
     * @return Document
     */
    public static function fetchByName(string $name)
    {
      $db = Database::getInstance();
      $query = "SELECT * FROM document WHERE name = `$name` LIMIT 1";
      $row = $db->query($query);
      return new self($row);
    }
    /**
     * Returns all Documents.
     *
     * @return Document[]
     */
    public static function getAllDocuments()
    {
        // to be implemented later
    }
}
