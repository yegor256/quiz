<?php

final class Document
{
    /**
     * @var string
     */
    private $name;

    /**
     * @var Database
     */
    private $db;

    /**
     * Document constructor.
     * @param string $name
     * @param Database $db
     */
    public function __construct($name, $db)
    {
        $this->name = $name;
        $this->db = $db;
    }

    public function title()
    {
        return $this->dbRowColumn(3); // third column in a row
    }

    public function content()
    {
        return $this->dbRowColumn(6); // sixth column in a row
    }

    private function dbRowColumn($column)
    {
        $pattern = 'SELECT * FROM document WHERE name = "%s" LIMIT 1';
        $sql = sprintf($pattern, $this->name);
        $row = $this->db->query($sql);
        return $row[$column];
    }
}

final class Documents
{
    public function all()
    {
        // to be implemented later
        return [];
    }
}

final class User
{
    private $db;

    public function __construct(Database $db)
    {
        $this->db = $db;
    }

    public function newDocument($name)
    {
        return new Document($name, $this->db);
    }

    public function ownDocuments()
    {
        $list = [];
        foreach ((new Documents)->all() as $doc) {
            if ($doc->user == $this)
                $list[] = $doc;
        }
        return $list;
    }

}
