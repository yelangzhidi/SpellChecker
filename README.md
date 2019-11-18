# CS245-Assignment1: SpellChecker
SpellChecker using Trie and Levenshtein Distance
# Implementation arguments:
There are two arguments in main: input and output.
input: the name of input file
output: the name of output file(if not exists, will create one)
# Running Time for Trie
Trie.insert() == O(L) where L is the longest vocabulary in dictionary.
Trie.search() == O(L) where L is the longest vocabulary in dictionary.
Trie.suggest() == O(26^L * T) where L is the longest vocabulary in dictionary.
And T is the length of target String.
# Running Time for Search Tree
Tree.insert() == O(log(n)) where n is how many vocabularies in the dictionary.
Tree.search() == O(log(n)) where n is how many vocabularies in the dictionary.
Tree.suggest() == O(log(n)) where n is how many vocabularies in the dictionary.
# Extra Credit:
I have finished extra credit 1 and 2.

I have directly read english.0 file from github in CS245A1.readDictFromUrl().
I have using least Levenshtein Distance find the closest word in Trie.suggest()