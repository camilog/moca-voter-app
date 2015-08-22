# VoterApp
App for the Voters, which is part of the [*MoCa QR*](https://github.com/CamiloG/moca_qr) Voting System project.

Android app for the voter to sign the encryption showed by [BallotSelection](https://github.com/CamiloG/BallotSelection) and to check if her vote is present on the BB.

## Files
1. **MainActivity.java**:

2. **ConfigurationActivity.java**:

3. **SignatureActivity.java**:

4. **ShowSignatureActivity.java**:

### Minimum Requirements
### Hardware

### Apps installed


## How to Use
* Make sure you satisfy the minimum requirements described above.
* Install the .apk file, which can be downloaded from [here](https://github.com/CamiloG/moca_qe/blob/master/Voter_Apps/signatureApp.apk?raw=true).

### Configuration
* At the moment that the voter receive the private key of the signature, select 'Configure Private Key', then the voter must scan the QR-Code showed by VoterKeyGenerator.

### Signature Process
* When BallotSelection shows the encrypted ballot in a QR-Code, asks to the voter to sign it using this app.
* The voter scans the QR-Code of the encryption.
* The app, using the private key recorded before, signs the encrypted ballot (in background).
* After the signing process, the app shows in a QR-Code the signature produced.
* The voter must show the QR-Code of the signature to BallotSelection, after this SignatureApp finishes and the voter must keep using BallotSelection.

### Verify vote present on BB
*Not implemented yet.*
