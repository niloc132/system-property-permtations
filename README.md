This project is a quick attempt at using `System.getProperty` and generated sources (via APT or some
other mechanism) in place of `GWT.create` and `<replace-with>` or `<generate-with>` rules to provide
multiple alternate runtime implementations.

There are three HTML files, each of which has a different setting for `window.locale`,
[en.html](https://niloc132.github.io/system-property-permtations/en.html),
[fr.html](https://niloc132.github.io/system-property-permtations/fr.html), and
[es.html](https://niloc132.github.io/system-property-permtations/es.html).
Here is en.html:

```
<!doctype html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <script type="text/javascript" language="javascript">window.locale='en';</script>

    <script type="text/javascript" language="javascript" src="sysprops.nocache.js"></script>
</head>
<body>
</body>
</html>
```

The module file then describes these three properties, and simple selection script to pick out the
`window.locale` above:

```
  <define-property name="locale" values="en,fr,es" />
  <property-provider name="locale">
    return window.locale;
  </property-provider>
```

And finally the entrypoint has a simple switch case to select an implementation based on the value of
`System.getProperty("locale"):

```
        Window.getSelf().alert("Locale is " + System.getProperty("locale"));

        Window.getSelf().alert("Greeting is " + constants.hello());
```

```

```

At present however, the wrong property is read from `System.getProperty`, and so the wrong implementation
is selected, and only one permutation is compiled:

```
[INFO] --- gwt-maven-plugin:2.8.0:compile (default) @ system-property-permutations ---
[INFO] auto discovered modules [com.colinalworth.gwt.sysprops.SystemPropertyBasedPermutations]
[INFO] Compiling module com.colinalworth.gwt.sysprops.SystemPropertyBasedPermutations
[INFO]    Compiling 1 permutation
[INFO]       Compiling permutation 0...
[INFO]    Compile of permutations succeeded
[INFO]    Compilation succeeded -- 4.529s
[INFO] Linking into /Users/colin/workspace/system-property-permutations/target/system-property-permutations-1.0-SNAPSHOT/sysprops
[INFO]    Link succeeded
[INFO]    Linking succeeded -- 0.206s
```

The unflattenKeylistIntoAnswers seems to be generated with not only soft permutations where it isn't
appropriate, but also seems to think that three permutations are required:

```
      unflattenKeylistIntoAnswers(['en'], 'F03147777FEB034474EBDDEB584EAAEB');
      unflattenKeylistIntoAnswers(['es'], 'F03147777FEB034474EBDDEB584EAAEB');
      unflattenKeylistIntoAnswers(['fr'], 'F03147777FEB034474EBDDEB584EAAEB');
      unflattenKeylistIntoAnswers(['en'], 'F03147777FEB034474EBDDEB584EAAEB' + ':1');
      unflattenKeylistIntoAnswers(['es'], 'F03147777FEB034474EBDDEB584EAAEB' + ':1');
      unflattenKeylistIntoAnswers(['fr'], 'F03147777FEB034474EBDDEB584EAAEB' + ':1');
      unflattenKeylistIntoAnswers(['en'], 'F03147777FEB034474EBDDEB584EAAEB' + ':2');
      unflattenKeylistIntoAnswers(['es'], 'F03147777FEB034474EBDDEB584EAAEB' + ':2');
      unflattenKeylistIntoAnswers(['fr'], 'F03147777FEB034474EBDDEB584EAAEB' + ':2');
      strongName = answers[computePropValue('locale')];
```

I think that this results in `fr` always being selected. Am I doing this wrong, or is
`System.getProperty` only usable for properties which actually have a completely different implementation
via `<replace-with>` or `<generate-with>`?