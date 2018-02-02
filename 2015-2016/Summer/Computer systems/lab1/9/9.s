
9.o:     file format elf64-x86-64


Disassembly of section .text:

0000000000000000 <main>:
   0:	48 83 ec 28          	sub    $0x28,%rsp
   4:	48 8d 4c 24 18       	lea    0x18(%rsp),%rcx
   9:	48 8d 54 24 10       	lea    0x10(%rsp),%rdx
   e:	48 8d 74 24 08       	lea    0x8(%rsp),%rsi
  13:	bf 00 00 00 00       	mov    $0x0,%edi
  18:	b8 00 00 00 00       	mov    $0x0,%eax
  1d:	e8 00 00 00 00       	callq  22 <main+0x22>
  22:	48 8b 44 24 08       	mov    0x8(%rsp),%rax
  27:	48 8b 4c 24 10       	mov    0x10(%rsp),%rcx
  2c:	48 01 c1             	add    %rax,%rcx
  2f:	48 89 ca             	mov    %rcx,%rdx
  32:	48 8b 04 25 00 00 00 	mov    0x0,%rax
  39:	00 
  3a:	48 0f 49 04 25 00 00 	cmovns 0x0,%rax
  41:	00 00 
  43:	48 0f 40 d0          	cmovo  %rax,%rdx
  47:	48 89 4c 24 10       	mov    %rcx,0x10(%rsp)
  4c:	48 8b 74 24 18       	mov    0x18(%rsp),%rsi
  51:	48 01 c6             	add    %rax,%rsi
  54:	48 89 f1             	mov    %rsi,%rcx
  57:	48 8b 04 25 00 00 00 	mov    0x0,%rax
  5e:	00 
  5f:	48 0f 49 04 25 00 00 	cmovns 0x0,%rax
  66:	00 00 
  68:	48 0f 40 c8          	cmovo  %rax,%rcx
  6c:	48 89 44 24 08       	mov    %rax,0x8(%rsp)
  71:	48 89 74 24 18       	mov    %rsi,0x18(%rsp)
  76:	be 00 00 00 00       	mov    $0x0,%esi
  7b:	bf 01 00 00 00       	mov    $0x1,%edi
  80:	b8 00 00 00 00       	mov    $0x0,%eax
  85:	e8 00 00 00 00       	callq  8a <main+0x8a>
  8a:	b8 00 00 00 00       	mov    $0x0,%eax
  8f:	48 83 c4 28          	add    $0x28,%rsp
  93:	c3                   	retq   
