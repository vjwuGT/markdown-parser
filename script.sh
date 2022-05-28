for file in test-files/*.md;
do
  echo $filename
  java MarkdownParse $file
done
