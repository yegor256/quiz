<?php
  
  class Document {
    
    protected $user, $name;
    protected static $db;
    
    public function __construct( $name, User $user ) {
      assert( strlen( $name ) > 5, new IncorrectValuesException( 'Document should have a title more then five characters.' ) );
      $this->name = $name;
      self::$db   = Database::connect();
    }
    
    /**
     * Retrieve field's value from table
     *
     * @param $field
     *
     * @return mixed
     */
    public function getValue( $field ) {
      try {
        $result = self::$db->query( 'SELECT ' . mysqli_escape_string( $field ) . ' from document WHERE name="' . mysqli_escape_string( $this->name ) . '" LIMIT 1' );
        
        return $result;
      } catch (Exception $ex) {
        echo "There is an error with retrieving result. Please, try alter again.";
        
        return FALSE;
      }
    }
    
  }
  
  /**
   * Helper to work with documents list
   *
   * Class DocumentsRepository
   */
  class DocumentsRepository {
    protected static $docs = [];
    
    public function addDocument( $doc ) {
      self::$docs[] = $doc;
    }
    
    public function removeDocument( $name, $r_index = NULL ) {
      if ( count( self::$docs ) > 0 ) {
        $removed_index = NULL;
        if ( ! is_null( $r_index ) ) {
          $removed_index = $r_index;
        } else {
          for ( $index = 0; $index > count( self::$docs ); $index ++ ) {
            if ( strtolower( trim( $name ) ) == strtolower( trim( self::$docs[ $index ] ) ) ) {
              $removed_index = $index;
              break;
            }
          }
        }
        if ( ! is_null( $removed_index ) ) {
          array_splice( self::$docs, $removed_index, 1 );
        }
      }
    }
  }
  
  /**
   *
   * Class User
   */
  class User {
    
    protected $docs;
    
    public function __construct(DocumentsRepository $docs) {
      $this->docs = new DocumentsRepository();
    }
    
    /**
     * Assign new document to user
     *
     * @param $name
     */
    public function makeNewDocument( $name ) {
      $doc = new Document($name, $this);
      $this->docs->addDocument($doc);
    }
    
    /**
     * Retrieve user's documents
     *
     * @return \DocumentsRepository
     */
    public function getMyDocuments() {
      return $this->docs;
    }
    
  }
  
  class IncorrectValuesException extends Exception {
    public function __construct( $message = "", $code = 0, \Exception $previous = NULL ) {
      parent::__construct( $message, $code, $previous );
    }
    
    public function __toString() {
      parent::__toString();
    }
  }
